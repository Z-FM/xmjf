package com.shsxt.xm.service.impl;

import com.shsxt.xm.db.dao.BasUserSecurityDao;
import com.shsxt.xm.po.BasUserSecurity;
import com.shsxt.xm.service.IBasUserSecurityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by GXR on 2017/11/12.
 */
@Service
public class BasUserSecurityServiceImpl implements IBasUserSecurityService {

    @Resource
    private BasUserSecurityDao basUserSecurityDao;
    @Override
    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId) {
        return basUserSecurityDao.queryBasUserSecurityByUserId(userId);
    }
}
