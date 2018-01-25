package com.zhoutc.monitor.enums;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-22 10:10
 */
public enum SwitchEnum {
    MONITOR(1,"告警监控开关"),
    REPORTER(2,"上报系统开关");

    private int code;
    private String desc;

    SwitchEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
