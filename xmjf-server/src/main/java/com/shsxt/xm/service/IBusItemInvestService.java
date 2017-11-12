package com.shsxt.xm.service;

import com.shsxt.xm.query.BusItemInvestQuery;
import com.shsxt.xm.utils.PageList;

/**
 * Created by GXR on 2017/11/12.
 */
public interface IBusItemInvestService {
    public PageList queryBusItemsByParams(BusItemInvestQuery busItemInvestQuery);
}
