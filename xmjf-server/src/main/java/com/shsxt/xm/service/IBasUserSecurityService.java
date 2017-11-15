package com.shsxt.xm.service;

import com.shsxt.xm.po.BasUserSecurity;

/**
 * Created by GXR on 2017/11/12.
 */
public interface IBasUserSecurityService {
    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId);

    /**
     * 根据登录用户id 查询用户认证状态
     * @param userId
     */
    public  void checkUserAuthStatus(Integer userId);

    /**
     * 用户认证
     * @param realName
     * @param card
     * @param password
     * @param userId
     */
    public void updateBasUserSecurityStatus(String realName,String card,String password,Integer userId);
    public  void updateUserSecurityInfo(Integer userId,String realName,String idCard,String payPassword);
}
