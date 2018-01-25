package com.zhoutc.monitor.enums;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-22 10:10
 */
public enum  NodeLevelEnum {
    GROUP(1,"group级别结点监控,监控/monitor"),
    APPLICATION(2,"application级别结点监控,,监控/monitor/group");

    private int code;
    private String desc;

    NodeLevelEnum(int code, String desc){
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
