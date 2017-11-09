package com.shsxt.xm.dto;

import com.shsxt.xm.po.BasItem;

/**
 * Created by GXR on 2017/11/9.
 */
public class BasItemDto extends BasItem{
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
