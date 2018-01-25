package com.zhoutc.monitor.entity;

import java.io.Serializable;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 10:51
 */
public class OsInfo implements Serializable{
    OsDetail detail;

    public OsDetail getDetail() {
        return detail;
    }

    public void setDetail(OsDetail detail) {
        this.detail = detail;
    }
}
