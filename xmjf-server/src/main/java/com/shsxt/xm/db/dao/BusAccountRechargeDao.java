package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.po.BusAccountRecharge;
import com.shsxt.xm.query.BusAccountRechargeQuery;

import java.util.List;

public interface BusAccountRechargeDao extends BaseDao<BusAccountRecharge> {

    public  BusAccountRecharge queryBusAccountRechargeByOrderNo(String orderNo);

    public List<BusAccountRecharge> queryAccountRechargeListByParams(BusAccountRechargeQuery busAccountRechargeQuery);

}