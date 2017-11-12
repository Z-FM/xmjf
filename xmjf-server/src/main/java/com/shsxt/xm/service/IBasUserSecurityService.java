package com.shsxt.xm.service;

import com.shsxt.xm.po.BasUserSecurity;

/**
 * Created by GXR on 2017/11/12.
 */
public interface IBasUserSecurityService {
    public BasUserSecurity queryBasUserSecurityByUserId(Integer userId);
}
