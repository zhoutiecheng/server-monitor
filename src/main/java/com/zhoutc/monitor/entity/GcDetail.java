package com.zhoutc.monitor.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 10:56
 */
public class GcDetail implements Serializable {
    private String name;
    private List<String> memoryPoolNames;
    private long count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMemoryPoolNames() {
        return memoryPoolNames;
    }

    public void setMemoryPoolNames(List<String> memoryPoolNames) {
        this.memoryPoolNames = memoryPoolNames;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
