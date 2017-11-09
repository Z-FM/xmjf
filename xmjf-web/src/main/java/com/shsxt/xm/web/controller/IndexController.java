package com.shsxt.xm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
/**
 * Created by GXR on 2017/11/7.
 */
@Controller
public class IndexController {

    @RequestMapping("index")
    public  String  index(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "index";
    }

}
