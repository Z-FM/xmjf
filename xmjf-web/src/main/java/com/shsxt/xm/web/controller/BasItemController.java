package com.shsxt.xm.web.controller;

import com.shsxt.xm.query.BasItemQuery;
import com.shsxt.xm.service.IBasItemService;
import com.shsxt.xm.utils.PageList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by GXR on 2017/11/9.
 */
@Controller
@RequestMapping("basItem")
public class BasItemController {
    @Resource
    private IBasItemService iBasItemService;

    @RequestMapping("queryBasItemsByParams")
    @ResponseBody
    public PageList queryBasItemsByParams(BasItemQuery basItemQuery){
        return  iBasItemService.queryBasItemsByParams(basItemQuery);
    }
}
