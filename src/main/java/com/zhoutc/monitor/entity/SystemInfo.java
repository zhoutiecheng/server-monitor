package com.zhoutc.monitor.entity;

import java.io.Serializable;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 10:33
 */
public class SystemInfo implements Serializable{
    //jvm信息
    JvmInfo jvm;
    //操作系统信息
    OsInfo os;
    //环境变量
    PropertiesInfo properties;



    public JvmInfo getJvm() {
        return jvm;
    }

    public void setJvm(JvmInfo jvm) {
        this.jvm = jvm;
    }

    public OsInfo getOs() {
        return os;
    }

    public void setOs(OsInfo os) {
        this.os = os;
    }

    public PropertiesInfo getProperties() {
        return properties;
    }

    public void setProperties(PropertiesInfo properties) {
        this.properties = properties;
    }
}
