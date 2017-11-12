package com.shsxt.xm.service;

import com.shsxt.xm.po.BusItemLoan;

/**
 * Created by GXR on 2017/11/12.
 */
public interface IBusItemLoanService {
    public BusItemLoan queryBusItemLoanByItemId(Integer itemId);
}
