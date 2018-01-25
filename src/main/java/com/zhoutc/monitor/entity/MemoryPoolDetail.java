package com.zhoutc.monitor.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 11:02
 */
public class MemoryPoolDetail implements Serializable {
    private  List<String> memoryManagerNames;
    private  MemUseDetail use;

    public List<String> getMemoryManagerNames() {
        return memoryManagerNames;
    }

    public void setMemoryManagerNames(List<String> memoryManagerNames) {
        this.memoryManagerNames = memoryManagerNames;
    }

    public MemUseDetail getUse() {
        return use;
    }

    public void setUse(MemUseDetail use) {
        this.use = use;
    }
}
