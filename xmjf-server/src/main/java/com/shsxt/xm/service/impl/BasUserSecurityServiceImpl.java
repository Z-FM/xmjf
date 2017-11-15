package com.shsxt.xm.service.impl;

import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.db.dao.BasUserSecurityDao;
import com.shsxt.xm.po.BasUserSecurity;
import com.shsxt.xm.service.IBasUserSecurityService;
import com.shsxt.xm.service.IBasUserService;
import com.shsxt.xm.utils.AssertUtil;
import com.shsxt.xm.utils.MD5;
import com.shsxt.xm.utils.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by GXR on 2017/11/12.
 */
@Service
public class BasUserSecurityServiceImpl implements IBasUserSecurityService {

    @Resource
    private BasUserSecurityDao basUserSecurityDao;
    @Resource
    private IBasUserService iBasUserService;

    @Override
    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId) {
        return basUserSecurityDao.queryBasUserSecurityByUserId(userId);
    }

    @Override
    public void checkUserAuthStatus(Integer userId) {
        AssertUtil.isTrue(null==userId||null==iBasUserService.queryBasUserByUserId(userId),"用户未登陆或用户不存在");
        BasUserSecurity basUserSecurity = this.queryBasUserSecurityByUserId(userId);
        AssertUtil.isTrue(basUserSecurity.getRealnameStatus().equals(0),"该用户未进行实名认证操作，请先执行认证处理");
    }

    @Override
    public void updateBasUserSecurityStatus(String realName, String card, String password, Integer userId) {
        AssertUtil.isTrue(StringUtils.isBlank(realName),"真实姓名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(password),"支付密码不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(card),"身份证号码不能为空");
        AssertUtil.isTrue(null==userId||null==iBasUserService.queryBasUserByUserId(userId),"用户未登录或用户不存在");
        AssertUtil.isTrue(null!=basUserSecurityDao.queryBasUserSecurityByUserId(card),"身份证号已存在!");
        password= MD5.toMD5(password);
        BasUserSecurity basUserSecurity=basUserSecurityDao.queryBasUserSecurityByUserId(userId);
        basUserSecurity.setVerifyTime(new Date());
        basUserSecurity.setRealnameStatus(1);
        basUserSecurity.setRealname(realName);
        basUserSecurity.setPaymentPassword(password);
        basUserSecurity.setIdentifyCard(card);
        AssertUtil.isTrue(basUserSecurityDao.update(basUserSecurity)<1, P2pConstant.OP_FAILED_MSG);
    }

    /**
     * 更新用户安全信息
     * @param userId
     * @param realName
     * @param idCard
     * @param payPassword
     */
    public  void updateUserSecurityInfo(Integer userId,String realName,String idCard,String payPassword){
        BasUserSecurity basUserSecurity=basUserSecurityDao.queryBasUserSecurityByUserId(userId);
        basUserSecurity.setRealname(realName);
        basUserSecurity.setIdentifyCard(idCard);
        basUserSecurity.setPaymentPassword(payPassword);
        basUserSecurity.setRealnameStatus(1);
        basUserSecurity.setVerifyTime(new Date());
        AssertUtil.isTrue(basUserSecurityDao.update(basUserSecurity)<1, P2pConstant.OP_FAILED_MSG);
    }
}
