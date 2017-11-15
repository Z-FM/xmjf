package com.shsxt.xm.enums;

/**
 * Created by GXR on 2017/11/14.
 */
public enum RechargeType {
    APP(1),// app 端
    BG(2), // 后台
    WEB(3),
    WEIXIN(4);
    private Integer type;

    RechargeType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
