package com.shsxt.xm.web.proxy;

import com.shsxt.xm.po.BasUser;
import com.shsxt.xm.service.IBasUserService;
import com.shsxt.xm.utils.AssertUtil;
import com.shsxt.xm.web.annotations.IsLogin;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by GXR on 2017/11/16.
 */
@Component
@Aspect
public class LoginProxy {
    @Autowired
    private HttpSession session;
    @Resource
    private IBasUserService iBasUserService;

    @Pointcut(value = "@annotation(com.shsxt.xm.web.annotations.IsLogin)")
    public void pointcut(){
    }
    @Before(value = "pointcut() && @annotation(isLogin)")
    public void checkIsLogin(IsLogin isLogin) {
        BasUser basUser= (BasUser) session.getAttribute("user");
        AssertUtil.isLogin(null==basUser||null==iBasUserService.queryBasUserByUserId(basUser.getId()),"用户未登录!");
    }

}
