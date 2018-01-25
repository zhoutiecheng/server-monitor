package com.zhoutc.monitor.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 11:01
 */
public class CompilationDetail implements Serializable {
    private String name;
    private Date totalCompilationTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTotalCompilationTime() {
        return totalCompilationTime;
    }

    public void setTotalCompilationTime(Date totalCompilationTime) {
        this.totalCompilationTime = totalCompilationTime;
    }
}
