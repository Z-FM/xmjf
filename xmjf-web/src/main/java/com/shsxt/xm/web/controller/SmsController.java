package com.shsxt.xm.web.controller;

import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.exceptions.ParamsExcetion;
import com.shsxt.xm.service.ISmsService;
import com.shsxt.xm.web.model.ResultInfo;
import com.shsxt.xm.web.utils.RandomCodesUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by GXR on 2017/11/7.
 */
@Controller
@RequestMapping("sms")
public class SmsController extends BaseController {
    @Resource
    private ISmsService smsService;

    @Override
    public void preMethod(HttpServletRequest request) {
        super.preMethod(request);
    }

    @RequestMapping("sendPhoneVerifyCode")
    @ResponseBody
    public ResultInfo sendPhoneVerifyCode(String phone,
                                          String picVerifyCode, HttpSession session){
        String sessionPicVerifyCode = (String)session.getAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        ResultInfo resultInfo = null;
        if(null==sessionPicVerifyCode){
            return failed("图片验证码已失效");
        }
        if(!picVerifyCode.equals(sessionPicVerifyCode)){
            return  failed("图片验证码不匹配");
        }
        //删除图片session
        //session.removeAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        //发送短信验证码
        String code = RandomCodesUtils.createRandom(true,4);
        try {
            smsService.sendPhoneCode(phone,code);
            //存储验证码设置验证码时间
            session.setAttribute(P2pConstant.PHONE_VERIFY_CODE+phone,code);
            session.setAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIR_TIME+phone,new Date());
            resultInfo=success("短信发送成功");
        }catch (ParamsExcetion paramsExcetion){
            resultInfo= failed(paramsExcetion.getErrorMsg());
        }catch (Exception e) {
            e.printStackTrace();
            resultInfo= failed("验证码发送失败,请稍后再试!");
        }
        return resultInfo;
    }

}
