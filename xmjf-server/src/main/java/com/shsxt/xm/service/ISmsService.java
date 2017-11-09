package com.shsxt.xm.service;

/**
 * Created by GXR on 2017/11/7.
 */
public interface ISmsService {
    /**
     * 发送手机短信验证码
     * @param phone
     * @param code
     * @throws Exception
     */
    public  void sendPhoneCode(String phone, String code) throws  Exception;
}
