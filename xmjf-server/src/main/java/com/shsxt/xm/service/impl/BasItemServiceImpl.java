package com.shsxt.xm.service.impl;

import com.github.pagehelper.PageHelper;
import com.shsxt.xm.db.dao.BasItemDao;
import com.shsxt.xm.dto.BasItemDto;
import com.shsxt.xm.query.BasItemQuery;
import com.shsxt.xm.service.IBasItemService;
import com.shsxt.xm.utils.PageList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by GXR on 2017/11/9.
 */
@Service
public class BasItemServiceImpl implements IBasItemService {
    @Resource
    private BasItemDao basItemDao;
    @Override
    public PageList queryBasItemsByParams(BasItemQuery basItemQuery) {
        PageHelper.startPage(basItemQuery.getPageNum(),basItemQuery.getPageSize());
        List<BasItemDto> basItemDtos = basItemDao.queryBasItemsByParams(basItemQuery);
        PageList pageList = new PageList(basItemDtos);
        return pageList;
    }
}
