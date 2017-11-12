package com.shsxt.xm.db.dao;

import com.shsxt.xm.base.BaseDao;
import com.shsxt.xm.po.BasUserSecurity;

public interface BasUserSecurityDao extends BaseDao<BasUserSecurity> {
    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId);
}