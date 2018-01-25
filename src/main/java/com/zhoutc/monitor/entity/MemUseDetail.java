package com.zhoutc.monitor.entity;

import java.io.Serializable;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 10:45
 */
public class MemUseDetail implements Serializable {
    private  double init;
    private  double used;
    private  double committed;
    private  double max;

    public double getInit() {
        return init;
    }

    public void setInit(double init) {
        this.init = init;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getCommitted() {
        return committed;
    }

    public void setCommitted(double committed) {
        this.committed = committed;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
