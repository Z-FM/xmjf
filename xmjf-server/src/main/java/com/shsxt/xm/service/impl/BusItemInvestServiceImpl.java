package com.shsxt.xm.service.impl;

import com.github.pagehelper.PageHelper;
import com.shsxt.xm.constant.ItemStatus;
import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.db.dao.*;
import com.shsxt.xm.dto.BusItemInvestDto;
import com.shsxt.xm.po.*;
import com.shsxt.xm.query.BusItemInvestQuery;
import com.shsxt.xm.service.IBusItemInvestService;
import com.shsxt.xm.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * Created by GXR on 2017/11/12.
 */
@Service
public class BusItemInvestServiceImpl implements IBusItemInvestService {
    @Resource
    private BusItemInvestDao busItemInvestDao;
    @Resource
    private BasUserDao basUserDao;
    @Resource
    private BasUserSecurityDao basUserSecurityDao;
    @Resource
    private BasItemDao basItemDao;
    @Resource
    private BusUserStatDao busUserStatDao;
    @Resource
    private BusAccountLogDao busAccountLogDao;
    @Resource
    private BusAccountDao busAccountDao;
    @Resource
    private BusUserIntegralDao busUserIntegralDao;
    @Override
    public PageList queryBusItemsByParams(BusItemInvestQuery busItemInvestQuery) {
        PageHelper.startPage(busItemInvestQuery.getPageNum(),busItemInvestQuery.getPageSize());
        List<BusItemInvestDto> busItemInvestDtos = busItemInvestDao.queryBusItemsByParams(busItemInvestQuery);
        PageList pageList = new PageList(busItemInvestDtos);
        return pageList;
    }

    /**
     *
     * @param amount    投资金额
     * @param itemId    项目id
     * @param password  交易密码
     * @param userId    用户id
     */
    @Override
    public void addBusItemInvest(BigDecimal amount, Integer itemId, String password, Integer userId) {
        AssertUtil.isTrue(null==userId|| null==basUserDao.queryById(userId),"用户非法");
        BasUserSecurity basUserSecurity = basUserSecurityDao.queryBasUserSecurityByUserId(userId);
        password= MD5.toMD5(password);
        AssertUtil.isTrue(StringUtils.isBlank(password),"交易密码非空!");
        AssertUtil.isTrue(!basUserSecurity.getPaymentPassword().equals(password),"交易密码不正确!");
        AssertUtil.isTrue(null==itemId,"投资id非法");
        BasItem basItem = basItemDao.queryById(itemId);
        AssertUtil.isTrue(null==basItem,"投资项目不存在");
        AssertUtil.isTrue(basItem.getMoveVip().equals(1),"移动项目，web不能进行操作");
        AssertUtil.isTrue(!basItem.getItemStatus().equals(ItemStatus.OPEN),"项目为开放，不能进行投资");
        BigDecimal syAmount = basItem.getItemAccount().add(basItem.getItemOngoingAccount().negate());
        int result = syAmount.compareTo(BigDecimal.ZERO);
        AssertUtil.isTrue(result<=0,"项目已满标");
        AssertUtil.isTrue(amount.compareTo(BigDecimal.ZERO)<=0,"投资金额非法");
        BigDecimal singleMinInvestAmount = basItem.getItemSingleMinInvestment();
        if(singleMinInvestAmount.compareTo(BigDecimal.ZERO)>0){
            AssertUtil.isTrue(amount.compareTo(singleMinInvestAmount)<0,"小于单笔最小投资金额");
        }
        BigDecimal singleMaxInvestAmount = basItem.getItemSingleMaxInvestment();
        if(singleMaxInvestAmount.compareTo(BigDecimal.ZERO)>0){
            if(amount.compareTo(singleMaxInvestAmount)>0){
                amount=singleMaxInvestAmount;
            }
        }
        AssertUtil.isTrue(syAmount.compareTo(singleMinInvestAmount)<0,"项目截标");
        if(amount.compareTo(singleMaxInvestAmount)<0&&amount.compareTo(singleMinInvestAmount)>0){
            if(amount.compareTo(syAmount)>0){
                amount=syAmount;
            }
        }
        int itemIsNew = (int)basItem.getItemIsnew();
        if(itemIsNew==1){
            BusUserStat busUserStat=busUserStatDao.queryBusUserStatByUserId(userId);
            AssertUtil.isTrue(busUserStat.getInvestCount()>0,"新手标不能重复投资!");
        }
        doInvest(amount,itemId,userId,basItem);
    }

    private void doInvest(BigDecimal amount, Integer itemId, Integer userId, BasItem basItem) {
        BusItemInvest busItemInvest=new BusItemInvest();
        busItemInvest.setActualCollectAmount(BigDecimal.ZERO);
        busItemInvest.setActualCollectInterest(BigDecimal.ZERO);
        busItemInvest.setActualCollectPrincipal(BigDecimal.ZERO);
        BigDecimal lx= Calculator.getInterest(amount,basItem);// 获取利息
        busItemInvest.setActualUncollectAmount(amount.add(lx));
        busItemInvest.setActualUncollectInterest(lx);
        busItemInvest.setActualUncollectPrincipal(amount);
        busItemInvest.setAddip(IpUtils.get());
        busItemInvest.setAdditionalRateAmount(BigDecimal.ZERO);
        busItemInvest.setAddtime(new Date());
        busItemInvest.setAutoId(null);
        busItemInvest.setCollectAmount(amount.add(lx));
        busItemInvest.setCollectInterest(lx);
        busItemInvest.setCollectPrincipal(amount);
        busItemInvest.setInvestAmount(amount);
        busItemInvest.setInvestCurrent(1);
        busItemInvest.setInvestDealAmount(amount);
        String investOrder="SXT_TZ"+RandomCodesUtils.createRandom(false,8)+userId;
        busItemInvest.setInvestOrder(investOrder);
        busItemInvest.setInvestStatus(0);
        busItemInvest.setInvestType(1);
        busItemInvest.setItemId(itemId);
        busItemInvest.setSpecialMarks(null);
        busItemInvest.setUpdatetime(new Date());
        busItemInvest.setUserId(userId);
        AssertUtil.isTrue(busItemInvestDao.insert(busItemInvest)<1, P2pConstant.OP_FAILED_MSG);
        // 积分更新
        BusUserIntegral busUserIntegral=busUserIntegralDao.queryBusUserInteGralByUserId(userId);
        busUserIntegral.setUsable(busUserIntegral.getUsable()+100);
        busUserIntegral.setTotal(busUserIntegral.getTotal()+100);
        AssertUtil.isTrue(busUserIntegralDao.update(busUserIntegral)<1,P2pConstant.OP_FAILED_MSG);
        // 更新用户统计信息
        BusUserStat busUserStat= busUserStatDao.queryBusUserStatByUserId(userId);
        busUserStat.setInvestCount(busUserStat.getInvestCount()+1);
        busUserStat.setInvestAmount(busUserStat.getInvestAmount().add(amount));
        AssertUtil.isTrue(busUserStatDao.update(busUserStat)<1,P2pConstant.OP_FAILED_MSG);

        // 总金额=可用金额+冻结金额+代收利息金额
        BusAccount busAccount= busAccountDao.queryBusAccountByUserId(userId);
        busAccount.setCash(busAccount.getCash().add(amount.negate()));// 设置可提现金额
        busAccount.setFrozen(busAccount.getFrozen().add(amount));// 设置冻结金额
        busAccount.setTotal(busAccount.getTotal().add(lx));// 设置总金额
        busAccount.setUsable(busAccount.getUsable().add(amount.negate()));// 可用金额
        busAccount.setWait(busAccount.getWait().add(lx));
        AssertUtil.isTrue(busAccountDao.update(busAccount)<1,P2pConstant.OP_FAILED_MSG);
        // 添加操作日志记录
        BusAccountLog busAccountLog=new BusAccountLog();
        busAccountLog.setAddtime(new Date());
        busAccountLog.setUserId(userId);
        busAccountLog.setAddip(IpUtils.get());
        busAccountLog.setOperMoney(amount);
        busAccountLog.setUsable(busAccount.getUsable());
        busAccountLog.setCash(busAccount.getCash());
        busAccountLog.setFrozen(busAccount.getFrozen());
        busAccountLog.setWait(busAccount.getWait());
        busAccountLog.setOperType("用户投标");
        busAccountLog.setTotal(busAccount.getTotal());
        busAccountLog.setRemark("用户投标成功!");
        busAccountLog.setRepay(BigDecimal.ZERO);
        busAccountLog.setBudgetType(2);
        busAccountLog.setTradeUserId(null);
        AssertUtil.isTrue(busAccountLogDao.insert(busAccountLog)<1,P2pConstant.OP_FAILED_MSG);

        // 更新项目投资进度信息
        basItem.setItemOngoingAccount(basItem.getItemOngoingAccount().add(amount));
        BigDecimal itemAccount=basItem.getItemAccount();
        BigDecimal result= basItem.getItemOngoingAccount().divide(basItem.getItemAccount(),2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        basItem.setItemScale(result);
        BigDecimal syAmount=basItem.getItemAccount().add(basItem.getItemOngoingAccount().negate());
        if(syAmount.compareTo(basItem.getItemSingleMinInvestment())<0){
            basItem.setItemStatus(ItemStatus.INTERCEPT_COMPLETE);
        }
        AssertUtil.isTrue(basItemDao.update(basItem)<1,P2pConstant.OP_FAILED_MSG);
    }
}
