package com.shsxt.xm.web.controller;

import com.shsxt.xm.query.BusItemInvestQuery;
import com.shsxt.xm.service.IBusItemInvestService;
import com.shsxt.xm.utils.PageList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
}
