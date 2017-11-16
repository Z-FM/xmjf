package com.shsxt.xm.web.controller;

import com.shsxt.xm.constant.P2pConstant;
import com.shsxt.xm.dto.BusAccountDto;
import com.shsxt.xm.dto.CallBackDto;
import com.shsxt.xm.dto.PayDto;
import com.shsxt.xm.exceptions.ParamsExcetion;
import com.shsxt.xm.po.BasUser;
import com.shsxt.xm.po.BasUserSecurity;
import com.shsxt.xm.po.BusAccountRecharge;
import com.shsxt.xm.query.BusAccountRechargeQuery;
import com.shsxt.xm.service.IBasUserSecurityService;
import com.shsxt.xm.service.IBusAccountRechargeService;
import com.shsxt.xm.service.IBusAccountService;
import com.shsxt.xm.utils.PageList;
import com.shsxt.xm.web.annotations.IsLogin;
import com.shsxt.xm.web.model.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * Created by lp on 2017/11/13.
 *   账户充值模块
 */
@Controller
@RequestMapping("account")
public class BusAccountRechargeController extends  BaseController {

    @Resource
   private IBusAccountRechargeService busAccountRechargeService;

    @Resource
    private IBasUserSecurityService basUserSecurityService;
    @Resource
    private IBusAccountService iBusAccountService;


    @IsLogin
    @RequestMapping("setting")
    public  String toSettingPage(HttpSession session,Model model){
        BasUser basUser= (BasUser) session.getAttribute("user");
        BasUserSecurity userSecurity=basUserSecurityService.queryBasUserSecurityByUserId(basUser.getId());
        model.addAttribute("security",userSecurity);
        return "/user/setting";
    }

    /**
     * 转至充值页面
     * @return
     */
    @IsLogin
    @RequestMapping("rechargePage")
    public  String recharge(){
        return "user/recharge";
    }

    @RequestMapping("addBusAccountRecharge")
    public  String toPayPage(BigDecimal amount,String picCode,String password,HttpSession session, Model model){
        BasUser basUser= (BasUser) session.getAttribute("user");
        String sessionPicCode= (String) session.getAttribute(P2pConstant.PICTURE_VERIFY_KEY);
        if(StringUtils.isBlank(sessionPicCode)){
            model.addAttribute("msg","交易验证码已过期!");
            return "user/recharge";
        }
        if(!sessionPicCode.equals(picCode)){
            model.addAttribute("msg","验证码不正确!");
            return "user/recharge";
        }
        try {
            session.removeAttribute(P2pConstant.PICTURE_VERIFY_KEY);
            BusAccountRecharge busAccountRecharge=new BusAccountRecharge();
            busAccountRecharge.setRechargeAmount(amount);
            busAccountRecharge.setUserId(basUser.getId());
            PayDto payDto=busAccountRechargeService.addBusAccountRechargeAndBuildPayInfo(busAccountRecharge,password);
            model.addAttribute("pay",payDto);
        } catch (ParamsExcetion e) {
            e.printStackTrace();
            model.addAttribute("msg",e.getErrorMsg());
            return "user/recharge";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("msg",P2pConstant.OP_FAILED_MSG);
            return "user/recharge";
        }
        return "user/pay";
    }
    @RequestMapping("callBack")
    public  String callBack(String trade_status,String out_order_no,BigDecimal total_fee,
                            String sign,HttpSession session){
        BasUser basUser= (BasUser) session.getAttribute("user");
        CallBackDto callBackDto=new CallBackDto();
        callBackDto.setTradeStatus(trade_status);
        callBackDto.setTotalFee(total_fee.toString());
        callBackDto.setOutOrderNo(out_order_no);
        callBackDto.setSign(sign);
        busAccountRechargeService.updateBusAccountRecharge(callBackDto,basUser.getId());
        return "user/recharge_record";
    }
    @IsLogin
    @RequestMapping("rechargeRecord")
    public  String toRechargeRecord(){
        return "user/recharge_record";
    }
    @RequestMapping("queryAccountRechargeListByParams")
    @ResponseBody
    public PageList  queryAccountRechargeListByParams(BusAccountRechargeQuery busAccountRechargeQuery, HttpSession session){
        BasUser basUser= (BasUser) session.getAttribute("user");
        busAccountRechargeQuery.setUserId(basUser.getId());
        return busAccountRechargeService.queryBusAccountRechargeLisrByParams(busAccountRechargeQuery);
    }
    @IsLogin
    @RequestMapping("auth")
    public  String toAuthPage(){
        return "user/auth";
    }
    @RequestMapping("userAuth")
    @ResponseBody
    public ResultInfo userAuth(String realName, String card, String password, HttpSession session){
        BasUser basUser= (BasUser) session.getAttribute("user");
        basUserSecurityService.updateBasUserSecurityStatus(realName,card,password,basUser.getId());
        return success("用户认证成功!");
    }
    @RequestMapping("queryAccountInfoByUserId")
    @ResponseBody
    public BusAccountDto queryAccountInfoByUserId(HttpSession session){
        BasUser basUser= (BasUser) session.getAttribute("user");
        return iBusAccountService.queryBusAccountInfoByUserId(basUser.getId());
    }

    @IsLogin
    @RequestMapping("accountInfo")
    public  String toAccountInfoPage(){
        return "user/account_info";
    }
}
