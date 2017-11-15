package com.shsxt.xm.service;

import com.shsxt.xm.dto.CallBackDto;
import com.shsxt.xm.dto.PayDto;
import com.shsxt.xm.po.BusAccountRecharge;
import com.shsxt.xm.query.BusAccountRechargeQuery;
import com.shsxt.xm.utils.PageList;

/**
 * Created by GXR on 2017/11/14.
 */
public interface IBusAccountRechargeService {
    public PayDto addBusAccountRechargeAndBuildPayInfo(BusAccountRecharge busAccountRecharge, String password);
    public void updateBusAccountRecharge(CallBackDto callBackDto, Integer userId);
    public PageList queryBusAccountRechargeLisrByParams(BusAccountRechargeQuery busAccountRechargeQuery);
}
