package com.shsxt.xm.service;

import com.shsxt.xm.po.BasUser;

import javax.servlet.http.HttpSession;

/**
 * Created by GXR on 2017/11/7.
 */
public interface IBasUserService {
    public BasUser queryBasUserByPhone(String phone);

    public void saveBasUser(String phone, String password, String picVerifyCode,
                               String phoneVerifyCode, HttpSession session);
    public BasUser userLogin(String phone,String password);
}
