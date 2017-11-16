package com.shsxt.xm.service;

import com.shsxt.xm.dto.BusAccountDto;
import com.shsxt.xm.po.BusAccount;

/**
 * Created by GXR on 2017/11/12.
 */
public interface IBusAccountService {
    public BusAccount queryBusAccount(Integer userId);
    public BusAccountDto queryBusAccountInfoByUserId(Integer userId);
}
