package com.shsxt.xm.dto;

import com.shsxt.xm.po.BasItem;

/**
 * Created by GXR on 2017/11/9.
 */
public class BasItemDto extends BasItem{
    private Integer total;

    public Long getSyTime() {
        return syTime;
    }

    public void setSyTime(Long syTime) {
        this.syTime = syTime;
    }

    private Long syTime;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
