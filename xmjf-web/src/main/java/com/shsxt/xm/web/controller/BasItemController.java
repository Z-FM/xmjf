package com.shsxt.xm.web.controller;

import com.shsxt.xm.po.BasItem;
import com.shsxt.xm.query.BasItemQuery;
import com.shsxt.xm.service.IBasItemService;
import com.shsxt.xm.utils.PageList;
import com.shsxt.xm.web.model.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by GXR on 2017/11/9.
 */
@Controller
@RequestMapping("basItem")
public class BasItemController extends  BaseController {
    @Resource
    private IBasItemService iBasItemService;

    @RequestMapping("basItemListPage")
    public  String toBasItemListPage(){
        return "item/invest_list";
    }

    @RequestMapping("queryBasItemsByParams")
    @ResponseBody
    public PageList queryBasItemsByParams(BasItemQuery basItemQuery){
        return  iBasItemService.queryBasItemsByParams(basItemQuery);
    }

    @RequestMapping("updateBasItemStatusToOpen")
    @ResponseBody
    public ResultInfo updateBasItemStatusToOpen(Integer itemId){
        iBasItemService.updateBasItemStatusToOpen(itemId);
        return success("项目开放成功!");
    }

    @RequestMapping("toBasItemDetailPage")
    public String toBasItemDetailPage(Integer itemId, Model model){
        BasItem basItem = iBasItemService.queryBasItemById(itemId);
        model.addAttribute("item",basItem);
        return "item/details";
    }
}
