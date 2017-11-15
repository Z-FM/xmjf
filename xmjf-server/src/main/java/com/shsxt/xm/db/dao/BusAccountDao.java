package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.po.BusAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface BusAccountDao extends BaseDao<BusAccount> {

    public BusAccount queryBusAccountByUserId(Integer userId);

    public Integer updateBusAccount(@Param("usableAmount")BigDecimal usableAmount,
                                    @Param("frozenAmount")BigDecimal frozenAmount,
                                    @Param("waitAmount")BigDecimal waitAmount,
                                    @Param("cashAmount")BigDecimal cashAmount,
                                    @Param("repayAmount") BigDecimal repayAmount,
                                    @Param("userId") Integer userId);

}