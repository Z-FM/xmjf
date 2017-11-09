package com.shsxt.xm.service.impl;

import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.db.dao.*;
import com.shsxt.xm.po.*;
import com.shsxt.xm.service.IBasUserService;
import com.shsxt.xm.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by GXR on 2017/11/7.
 */
@Service
public class BasUserServiceImpl implements IBasUserService{

    @Resource
    private BasUserDao basUserDao;
    @Resource
    private BasUserInfoDao basUserInfoDao;
    @Resource
    private BasUserSecurityDao basUserSecurityDao;

    @Resource
    private BusAccountDao busAccountDao;

    @Resource
    private BusUserIntegralDao busUserIntegralDao;

    @Resource
    private BusIncomeStatDao busIncomeStatDao;

    @Resource
    private BusUserStatDao busUserStatDao;


    @Resource
    private BasExperiencedGoldDao basExperiencedGoldDao;

    @Resource
    private SysLogDao sysLogDao;

    @Override
    public BasUser queryBasUserByPhone(String phone) {
        return basUserDao.queryBasUserByPhone(phone);
    }

    @Override
    public void saveBasUser(String phone, String password, String picVerifyCode,
                               String phoneVerifyCode, HttpSession session) {
        BasUser basUser = new BasUser();
        AssertUtil.isTrue(null!=queryBasUserByPhone(phone),"该手机号已注册");
        String sessionPicVerifyCode = (String)session.getAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        AssertUtil.isTrue(null==sessionPicVerifyCode,"图片验证码已失效");
        AssertUtil.isTrue(null!=picVerifyCode&&!picVerifyCode.equals(sessionPicVerifyCode),"图片验证码错误");
        Date sessionSmsDate = (Date)session.getAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIR_TIME+phone);
        String sessionPhoneCode = (String) session.getAttribute(P2pConstant.PHONE_VERIFY_CODE+phone);
        AssertUtil.isTrue(null==sessionPhoneCode||null==sessionSmsDate,"手机验证码已失效");
        long time = (new Date().getTime()-sessionSmsDate.getTime())/1000;
        AssertUtil.isTrue(time>P2pConstant.PHONE_VERIFY_CODE_EXPIR_TIME,"手机验证码已过期");
        AssertUtil.isTrue(!sessionPhoneCode.equals(phoneVerifyCode),"时间验证码错误");
        Integer userId= initBasUser(phone,password);
        // 初始化用户基本信息
        initBasUserInfo(userId);
        // 用户安全信息表
        initBasUserSecurity(userId);
        // 用户账户信息初始化
        initBusAccount(userId);
        // 用户基本初始化方法
        initBusUserIntegral(userId);
        // 用户收益初始化
        initBusIncomeStat(userId);
        // 初始化用户统计信息
        initBusUserStat(userId);
        //  用户体验今信息初始化
        initBasExperiencedGold(userId);
        // 初始化系统日志
        initSysLog(userId);
    }

    public BasUser userLogin(String phone,String password){
        return userLoginCheck(phone,password);
    }

    /**
     * 用户密码登陆参数校验
     * @param phone
     * @param password
     */
    private BasUser userLoginCheck(String phone, String password) {
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(password),"密码不能为空");
        BasUser basUser = queryBasUserByPhone(phone);
        AssertUtil.isTrue(null==basUser,"该用户不存在");
        AssertUtil.isTrue(basUser.getStatus()==0,"该用户已注销");
        password=Md5Util.encode(password+basUser.getSalt());
        AssertUtil.isTrue(!password.equals(basUser.getPassword()),"密码不正确");
        return basUser;
    }

    private void initSysLog(Integer userId) {
        SysLog sysLog=new SysLog();
        sysLog.setOperating("用户注册");
        sysLog.setAddtime(new Date());
        sysLog.setAddip(IpUtils.get());
        sysLog.setCode("register");
        sysLog.setResult(1);
        sysLog.setType(4);
        sysLog.setUserId(userId);
        AssertUtil.isTrue(sysLogDao.insert(sysLog)<1,P2pConstant.OP_FAILED_MSG);
    }

    private void initBasExperiencedGold(Integer userId) {
        BasExperiencedGold basExperiencedGold=new BasExperiencedGold();
        basExperiencedGold.setWay("register");
        basExperiencedGold.setUserId(userId);
        basExperiencedGold.setUsefulLife(10);
        basExperiencedGold.setStatus(2);
        basExperiencedGold.setRate(BigDecimal.valueOf(1));
        basExperiencedGold.setGoldName("注册体验金");
        basExperiencedGold.setExpiredTime(DateUtils.addDays(new Date(),30));
        basExperiencedGold.setAmount(BigDecimal.valueOf(2888));
        basExperiencedGold.setAddtime(new Date());
        basExperiencedGold.setAddip(IpUtils.get());
        AssertUtil.isTrue(basExperiencedGoldDao.insert(basExperiencedGold)<1,P2pConstant.OP_FAILED_MSG);

    }

    private void initBusUserStat(Integer userId) {
        BusUserStat busUserStat=new BusUserStat();
        busUserStat.setUserId(userId);
        busUserStat.setRechargeCount(0);
        busUserStat.setRechargeAmount(BigDecimal.ZERO);
        busUserStat.setInvestLaveAmount(BigDecimal.ZERO);
        busUserStat.setInvestCount(0);
        busUserStat.setInvestAmount(BigDecimal.ZERO);
        busUserStat.setCouponCount(0);
        busUserStat.setCouponAmount(BigDecimal.ZERO);
        busUserStat.setCashCount(0);
        busUserStat.setCashAmount(BigDecimal.ZERO);
        AssertUtil.isTrue(busUserStatDao.insert(busUserStat)<1,P2pConstant.OP_FAILED_MSG);
    }


    private void initBusIncomeStat(Integer userId) {
        BusIncomeStat busIncomeStat=new BusIncomeStat();
        busIncomeStat.setWaitIncome(BigDecimal.ZERO);
        busIncomeStat.setUserId(userId);
        busIncomeStat.setTotalIncome(BigDecimal.ZERO);
        busIncomeStat.setEarnedIncome(BigDecimal.ZERO);
        AssertUtil.isTrue(busIncomeStatDao.insert(busIncomeStat)<1,P2pConstant.OP_FAILED_MSG);
    }

    private void initBusUserIntegral(Integer userId) {
        BusUserIntegral busUserIntegral=new BusUserIntegral();
        busUserIntegral.setTotal(0);
        busUserIntegral.setUsable(0);
        busUserIntegral.setUserId(userId);
        AssertUtil.isTrue(busUserIntegralDao.insert(busUserIntegral)<1,P2pConstant.OP_FAILED_MSG);
    }

    private void initBusAccount(Integer userId) {
        BusAccount busAccount=new BusAccount();
        busAccount.setWait(BigDecimal.ZERO);
        busAccount.setUserId(userId);
        busAccount.setUsable(BigDecimal.ZERO);
        busAccount.setTotal(BigDecimal.ZERO);
        busAccount.setRepay(BigDecimal.ZERO);
        busAccount.setFrozen(BigDecimal.ZERO);
        busAccount.setCash(BigDecimal.ZERO);
        AssertUtil.isTrue(busAccountDao.insert(busAccount)<1,P2pConstant.OP_FAILED_MSG);
    }

    private void initBasUserSecurity(Integer userId) {
        BasUserSecurity basUserSecurity=new BasUserSecurity();
        basUserSecurity.setPhoneStatus(1);
        basUserSecurity.setUserId(userId);
        AssertUtil.isTrue(basUserSecurityDao.insert(basUserSecurity)<1,P2pConstant.OP_FAILED_MSG);;
    }

    private void initBasUserInfo(Integer userId) {
        BasUserInfo basUserInfo=new BasUserInfo();
        basUserInfo.setUserId(userId);
        basUserInfo.setCustomerType(0);
        String code= RandomCodesUtils.createRandom(false,5);
        basUserInfo.setInviteCode(code);
        basUserInfo.setCashLimit(0);
        AssertUtil.isTrue(basUserInfoDao.insert(basUserInfo)<1,P2pConstant.OP_FAILED_MSG);
    }

    /**
     * 用户基本信息添加
     * @param phone
     * @param password
     */
    private Integer initBasUser(String phone,String password) {
        BasUser basUser=new BasUser();
        //basUser.setUsername("sxt_p2p_"+UUID.randomUUID().toString().replace());
        basUser.setType(1);
        basUser.setStatus(1);
        String salt= RandomCodesUtils.createRandom(false,4);
        password= Md5Util.encode(password+salt);
        basUser.setSalt(salt);
        basUser.setReferer("PC");
        basUser.setPassword(password);
        basUser.setMobile(phone);
        basUser.setAddtime(new Date()) ;
        basUser.setAddip(IpUtils.get());
        AssertUtil.isTrue(basUserDao.insert(basUser)<1, P2pConstant.OP_FAILED_MSG);
        basUser.setUsername("SXT_P2P"+basUser.getId());
        AssertUtil.isTrue(basUserDao.update(basUser)<1,P2pConstant.OP_FAILED_MSG);
        return basUser.getId();
    }

}
