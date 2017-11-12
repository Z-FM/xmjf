package com.shsxt.xm.service;

import com.shsxt.xm.po.SysPicture;

import java.util.List;

/**
 * Created by GXR on 2017/11/12.
 */
public interface ISysPictureService {
    public List<SysPicture> querySysPicturesByItemId(Integer itemId);
}
