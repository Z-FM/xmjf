package com.shsxt.xm.web.controller;

import com.shsxt.xm.po.BasUser;
import com.shsxt.xm.query.BusItemInvestQuery;
import com.shsxt.xm.service.IBusItemInvestService;
import com.shsxt.xm.utils.PageList;
import com.shsxt.xm.web.model.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * Created by lp on 2017/11/11.
 */
@Controller
@RequestMapping("busItemInvest")
public class BusItemInvestController  extends  BaseController{
    @Resource
    private IBusItemInvestService busItemInvestService;


    @RequestMapping("queryBusItemInvestsByParams")
    @ResponseBody
    public PageList  queryBusItemsByParams(BusItemInvestQuery busItemInvestQuery){
        return busItemInvestService.queryBusItemsByParams(busItemInvestQuery);
    }

    @RequestMapping("doInvest")
    @ResponseBody
    public ResultInfo doInvest(BigDecimal amount, Integer itemId, String password, HttpSession session){
        BasUser basUser = (BasUser) session.getAttribute("user");
        busItemInvestService.addBusItemInvest(amount,itemId,password,basUser.getId());
        return success("投资成功");
    }
}
