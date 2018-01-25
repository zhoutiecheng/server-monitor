package com.zhoutc.monitor.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 10:34
 */
public class JvmInfo implements Serializable{
    private MemoryMXDetail memoryMX;
    private RunTimeDetail runTime;
    private ThreadDetail thread;
    private List<GcDetail> gc;
    private MemoryDetail memory;
    private CompilationDetail compilation;
    private List<MemoryPoolDetail> memoryPool;

    public MemoryMXDetail getMemoryMX() {
        return memoryMX;
    }

    public void setMemoryMX(MemoryMXDetail memoryMX) {
        this.memoryMX = memoryMX;
    }

    public RunTimeDetail getRunTime() {
        return runTime;
    }

    public void setRunTime(RunTimeDetail runTime) {
        this.runTime = runTime;
    }

    public ThreadDetail getThread() {
        return thread;
    }

    public void setThread(ThreadDetail thread) {
        this.thread = thread;
    }

    public List<GcDetail> getGc() {
        return gc;
    }

    public void setGc(List<GcDetail> gc) {
        this.gc = gc;
    }

    public MemoryDetail getMemory() {
        return memory;
    }

    public void setMemory(MemoryDetail memory) {
        this.memory = memory;
    }

    public CompilationDetail getCompilation() {
        return compilation;
    }

    public void setCompilation(CompilationDetail compilation) {
        this.compilation = compilation;
    }

    public List<MemoryPoolDetail> getMemoryPool() {
        return memoryPool;
    }

    public void setMemoryPool(List<MemoryPoolDetail> memoryPool) {
        this.memoryPool = memoryPool;
    }
}
