package com.shsxt.xm.service;

import com.shsxt.xm.query.BusItemInvestQuery;
import com.shsxt.xm.utils.PageList;

import java.math.BigDecimal;

/**
 * Created by GXR on 2017/11/12.
 */
public interface IBusItemInvestService {
    public PageList queryBusItemsByParams(BusItemInvestQuery busItemInvestQuery);
    public void addBusItemInvest(BigDecimal amount, Integer itemId, String password, Integer userId);
}
