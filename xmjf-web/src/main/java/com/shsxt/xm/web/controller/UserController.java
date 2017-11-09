package com.shsxt.xm.web.controller;

import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.po.BasUser;
import com.shsxt.xm.service.IBasUserService;
import com.shsxt.xm.web.model.ResultInfo;
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping("user")
public class UserController extends BaseController{

    @Resource
    private IBasUserService iBasUserService;

    @RequestMapping("login")
    public String Login(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "login";
    }

    @RequestMapping("register")
    public String Register(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "register";
    }

    /**
     * 注册接口
     * @param phone
     * @param password
     * @param picVerifyCode
     * @param phoneVerifyCode
     * @param session
     * @return
     */
    @RequestMapping("registerUser")
    @ResponseBody
    public ResultInfo registerUser(String phone, String password, String picVerifyCode,
                                   String phoneVerifyCode, HttpSession session){
        String sessionPicVerifyCode = (String)session.getAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        if(StringUtils.isBlank(sessionPicVerifyCode)){
            return failed("图片验证码失效!");
        }
        if(null!=sessionPicVerifyCode&&!(sessionPicVerifyCode.equals(picVerifyCode))){
            return failed("图片验证码错误!");
        }
        Date sessionSmsDate = (Date)session.getAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIR_TIME+phone);
        String sessionPhoneCode = (String) session.getAttribute(P2pConstant.PHONE_VERIFY_CODE+phone);
        if(null!=sessionSmsDate&&null!=sessionPhoneCode){
            long time = (new Date().getTime()-sessionSmsDate.getTime())/1000;
            if(time>P2pConstant.PHONE_VERIFY_CODE_EXPIR_TIME){
                 return failed("手机验证码已过期!");
            }
        }else{
            return failed("验证码已失效，请从新获取");
        }
        if (!sessionPhoneCode.equals(phoneVerifyCode)){
            return failed("手机验证码不正确");
        }
        iBasUserService.saveBasUser(phone,password,picVerifyCode,phoneVerifyCode,session);
        //resultInfo.setMsg("用户注册成功");
        // 注册成功 移除 session 信息 图片 session 收集验证码 session 验证码失效时间 session
        session.removeAttribute(P2pConstant.PHONE_VERIFY_CODE+phone);
        session.removeAttribute(P2pConstant.PHONE_VERIFY_CODE_EXPIR_TIME+phone);
        session.removeAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        return success("注册成功");
    }

    @RequestMapping("userLogin")
    @ResponseBody
    public  ResultInfo userLogin(String phone,String password,HttpSession session){
        BasUser basUser= iBasUserService.userLogin(phone,password);
        session.setAttribute("user",basUser);
        return success("用户登陆成功");

    }

    @RequestMapping("exit")
    public  String exit(HttpSession session){
        session.removeAttribute("user");
        return "login";
    }

}
