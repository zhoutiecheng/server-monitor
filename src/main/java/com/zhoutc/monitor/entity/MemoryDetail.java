package com.zhoutc.monitor.entity;

import java.io.Serializable;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 10:59
 */
public class MemoryDetail implements Serializable{
    private double total;
    private double free;
    private double max;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getFree() {
        return free;
    }

    public void setFree(double free) {
        this.free = free;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}

