package com.shsxt.xm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.xm.db.dao.BusItemInvestDao;
import com.shsxt.xm.dto.BusItemInvestDto;
import com.shsxt.xm.query.BusItemInvestQuery;
import com.shsxt.xm.service.IBusItemInvestService;
import com.shsxt.xm.utils.PageList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by GXR on 2017/11/12.
 */
@Service
public class BusItemInvestServiceImpl implements IBusItemInvestService {
    @Resource
    private BusItemInvestDao busItemInvestDao;
    @Override
    public PageList queryBusItemsByParams(BusItemInvestQuery busItemInvestQuery) {
        PageHelper.startPage(busItemInvestQuery.getPageNum(),busItemInvestQuery.getPageSize());
        List<BusItemInvestDto> busItemInvestDtos = busItemInvestDao.queryBusItemsByParams(busItemInvestQuery);
        PageList pageList = new PageList(busItemInvestDtos);
        return pageList;
    }
}
