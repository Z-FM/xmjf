package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.po.BasUserSecurity;
import org.apache.ibatis.annotations.Param;

public interface BasUserSecurityDao extends BaseDao<BasUserSecurity> {
    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId);
    public BasUserSecurity queryBasUserSecurityByUserId(@Param("card")String card);;
}