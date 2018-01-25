package com.zhoutc.monitor.entity;

import java.io.Serializable;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 11:27
 */
public class MemoryMXDetail implements Serializable{
    //堆使用信息
    private MemUseDetail heap;
    //非堆使用信息
    private MemUseDetail noHeap;
    private  double init;
    private  double used;
    private  double committed;
    private  double max;

    public MemUseDetail getHeap() {
        return heap;
    }

    public void setHeap(MemUseDetail heap) {
        this.heap = heap;
    }

    public MemUseDetail getNoHeap() {
        return noHeap;
    }

    public void setNoHeap(MemUseDetail noHeap) {
        this.noHeap = noHeap;
    }

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
