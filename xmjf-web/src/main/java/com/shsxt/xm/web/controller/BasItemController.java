package com.shsxt.xm.web.controller;

import com.shsxt.xm.po.*;
import com.shsxt.xm.query.BasItemQuery;
import com.shsxt.xm.service.*;
import com.shsxt.xm.utils.PageList;
import com.shsxt.xm.web.model.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by GXR on 2017/11/9.
 */
@Controller
@RequestMapping("basItem")
public class BasItemController extends  BaseController {
    @Resource
    private IBasItemService iBasItemService;
    @Resource
    private IBasUserSecurityService iBasUserSecurityService;
    @Resource
    private IBusItemLoanService iBusItemLoanService;
    @Resource
    private IBusAccountService iBusAccountService;
    @Resource
    private ISysPictureService iSysPictureService;
    @Resource
    private IBusItemInvestService busItemInvestService;

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
    public String toBasItemDetailPage(Integer itemId, Model model, HttpSession session){
        BasItem basItem = iBasItemService.queryBasItemById(itemId);
        model.addAttribute("item",basItem);
        //获取贷款人id
        Integer userId = basItem.getItemUserId();
        BasUserSecurity basUserSecurity = iBasUserSecurityService.queryBasUserSecurityByUserId(userId);
        model.addAttribute("loanUser",basUserSecurity);
        BusItemLoan busItemLoan = iBusItemLoanService.queryBusItemLoanByItemId(itemId);
        model.addAttribute("busItemLoan",busItemLoan);
        BasUser basUser= (BasUser) session.getAttribute("user");
        if(null!=basUser){
            BusAccount busAccount= iBusAccountService.queryBusAccount(basUser.getId());
            model.addAttribute("busAccount",busAccount);
        }
        List<SysPicture> sysPictures=iSysPictureService.querySysPicturesByItemId(itemId);
        model.addAttribute("pics",sysPictures);
        return "item/details";
    }

    @RequestMapping("doInvest")
    @ResponseBody
    public ResultInfo doInvest(BigDecimal amount, Integer itemId, String password, HttpSession session){
        BasUser basUser = (BasUser) session.getAttribute("user");
        busItemInvestService.addBusItemInvest(amount,itemId,password,basUser.getId());
        return success("投资成功");
    }
}
