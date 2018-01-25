package com.zhoutc.monitor.builder;


import com.zhoutc.monitor.entity.*;

import java.lang.management.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 11:06
 */
public class SystemBuider {

    private SystemBuider() {
    }

    public static CompilationDetail buildCompilation() {
        CompilationMXBean compilMBean = ManagementFactory.getCompilationMXBean();
        CompilationDetail compilationDetail = new CompilationDetail();
        compilationDetail.setName(compilMBean.getName());
        compilationDetail.setTotalCompilationTime(compilationDetail.getTotalCompilationTime());
        return compilationDetail;
    }

    public static List<GcDetail> buildGc() {
        List<GcDetail> list = new ArrayList<>();
        List<GarbageCollectorMXBean> gcMBeanList = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcMBean : gcMBeanList) {
            GcDetail gcDetail = new GcDetail();
            gcDetail.setName(gcMBean.getName());
            gcDetail.setMemoryPoolNames(Arrays.asList(gcMBean.getMemoryPoolNames()));
            gcDetail.setCount(gcMBean.getCollectionCount());
            list.add(gcDetail);
        }
        return list;
    }

    public static MemoryMXDetail buildMemoryMX() {
        MemoryMXDetail memoryMXDetail = new MemoryMXDetail();
        MemoryMXBean memoryMBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memoryMBean.getHeapMemoryUsage();
        memoryMXDetail.setInit(usage.getInit() / 1024d / 1024d);
        memoryMXDetail.setUsed(usage.getUsed() / 1024d / 1024d);
        memoryMXDetail.setMax(usage.getMax() / 1024d / 1024d);
        memoryMXDetail.setCommitted(usage.getCommitted() / 1024d / 1024d);

        MemoryUsage useHeap = memoryMBean.getHeapMemoryUsage();
        MemUseDetail memUseDetail = new MemUseDetail();
        memUseDetail.setInit(useHeap.getInit() / 1024d / 1024d);
        memUseDetail.setUsed(useHeap.getUsed() / 1024d / 1024d);
        memUseDetail.setMax(useHeap.getMax() / 1024d / 1024d);
        memUseDetail.setCommitted(useHeap.getCommitted() / 1024d / 1024d);
        memoryMXDetail.setHeap(memUseDetail);

        MemoryUsage useNoHeap = memoryMBean.getNonHeapMemoryUsage();
        MemUseDetail noHeapDetail = new MemUseDetail();
        noHeapDetail.setInit(useNoHeap.getInit() / 1024d / 1024d);
        noHeapDetail.setUsed(useNoHeap.getUsed() / 1024d / 1024d);
        noHeapDetail.setMax(useNoHeap.getMax() / 1024d / 1024d);
        noHeapDetail.setCommitted(useNoHeap.getCommitted() / 1024d / 1024d);
        memoryMXDetail.setNoHeap(noHeapDetail);
        return memoryMXDetail;
    }

    public static MemoryDetail buildMemory() {
        MemoryDetail memoryDetail = new MemoryDetail();
        double total = (double) Runtime.getRuntime().totalMemory() / 1024d / 1024d;
        double free = (double) Runtime.getRuntime().freeMemory() / 1024d / 1024d;
        double max = (double) Runtime.getRuntime().maxMemory() / 1024d / 1024d;
        memoryDetail.setFree(free);
        memoryDetail.setMax(max);
        memoryDetail.setTotal(total);
        return memoryDetail;
    }

    public static List<MemoryPoolDetail> buildMemoryPool() {
        List<MemoryPoolDetail> list = new ArrayList<>();
        List<MemoryPoolMXBean> mpMBeanList = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean mpMBean : mpMBeanList) {
            MemoryPoolDetail memoryPoolDetail = new MemoryPoolDetail();
            memoryPoolDetail.setMemoryManagerNames(Arrays.asList(mpMBean.getMemoryManagerNames()));
            MemUseDetail useDetail = new MemUseDetail();
            useDetail.setInit(mpMBean.getUsage().getInit()/1024d/1024d);
            useDetail.setMax(mpMBean.getUsage().getMax()/1024d/1024d);
            useDetail.setUsed(mpMBean.getUsage().getUsed()/1024d/1024d);
            useDetail.setCommitted(mpMBean.getUsage().getCommitted()/1024d/1024d);
            memoryPoolDetail.setUse(useDetail);
            list.add(memoryPoolDetail);
        }
        return list;
    }

    public static OsDetail buildOs() {
        OsDetail osDetail = new OsDetail();
        OperatingSystemMXBean osMBean = ManagementFactory.getOperatingSystemMXBean();
        osDetail.setName(osMBean.getName());
        osDetail.setArch(osMBean.getArch());
        osDetail.setVersion(osMBean.getVersion());
        osDetail.setAvailableProcessors(osMBean.getAvailableProcessors());
        return osDetail;
    }

    public static RunTimeDetail buildRunTime() {
        RunTimeDetail runTimeDetail = new RunTimeDetail();
        RuntimeMXBean runtimeMBean = ManagementFactory.getRuntimeMXBean();
        runTimeDetail.setCalssPath(runtimeMBean.getClassPath());
        runTimeDetail.setLibraryPath(runtimeMBean.getLibraryPath());
        runTimeDetail.setVmName(runtimeMBean.getVmName());
        runTimeDetail.setVmVersion(runtimeMBean.getVmVersion());
        runTimeDetail.setInputArguments(runtimeMBean.getInputArguments());
        return runTimeDetail;
    }

    public static ThreadDetail buildThread() {
        ThreadDetail threadDetail = new ThreadDetail();
        ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
        threadDetail.setCurrentThreadCpuTime(threadMBean.getCurrentThreadCpuTime());
        threadDetail.setCurrentThreadUserTime(threadMBean.getCurrentThreadUserTime());
        threadDetail.setDaemonThreadCount(threadMBean.getDaemonThreadCount());
        threadDetail.setThreadCount(threadMBean.getThreadCount());
        threadDetail.setPeakThreadCount(threadMBean.getPeakThreadCount());
        return threadDetail;
    }

    public static JvmInfo buildJvmInfo() {
        JvmInfo jvmInfo = new JvmInfo();
        jvmInfo.setCompilation(buildCompilation());
        jvmInfo.setGc(buildGc());
        jvmInfo.setMemory(buildMemory());
        jvmInfo.setMemoryMX(buildMemoryMX());
        jvmInfo.setMemoryPool(buildMemoryPool());
        jvmInfo.setRunTime(buildRunTime());
        jvmInfo.setThread(buildThread());
        return jvmInfo;
    }

    public static PropertiesInfo buildPropertiesInfo() {
        Properties props=System.getProperties(); //系统属性
        PropertiesInfo propertiesInfo = new PropertiesInfo();
        propertiesInfo.setJavaVersion(props.getProperty("java.version"));
        propertiesInfo.setJavaVendor(props.getProperty("java.vendor"));
        propertiesInfo.setJavaVendorUrl(props.getProperty("java.vendor.url"));
        propertiesInfo.setJavaHome(props.getProperty("java.home"));
        propertiesInfo.setJavaVmSpecificationVersion(props.getProperty("java.vm.specification.version"));
        propertiesInfo.setJavaVmSpecificationVendor(props.getProperty("java.vm.specification.vendor"));
        propertiesInfo.setJavaVmSpecificationName(props.getProperty("java.vm.specification.name"));
        propertiesInfo.setJavaVmVersion(props.getProperty("java.vm.version"));
        propertiesInfo.setJavaVmVendor(props.getProperty("java.vm.vendor"));
        propertiesInfo.setJavaVmName(props.getProperty("java.vm.name"));
        propertiesInfo.setJavaSpecificationVersion(props.getProperty("java.specification.version"));
        propertiesInfo.setJavaSpecificationVender(props.getProperty("java.specification.vender"));
        propertiesInfo.setJavaSpecificationName(props.getProperty("java.specification.name"));
        propertiesInfo.setJavaClassVersion(props.getProperty("java.class.version"));
        propertiesInfo.setJavaClassPath(props.getProperty("java.class.path"));
        propertiesInfo.setJavaLibraryPath(props.getProperty("java.library.path"));
        propertiesInfo.setJavaIoTmpdir(props.getProperty("java.io.tmpdir"));
        propertiesInfo.setJavaExtDirs(props.getProperty("java.ext.dirs"));
        propertiesInfo.setOsName(props.getProperty("os.name"));
        propertiesInfo.setOsArch(props.getProperty("os.arch"));
        propertiesInfo.setOsVersion(props.getProperty("os.version"));
        propertiesInfo.setFileSeparator(props.getProperty("file.separator"));
        propertiesInfo.setPathSeparator(props.getProperty("path.separator"));
        propertiesInfo.setLineSeparator(props.getProperty("line.separator"));
        propertiesInfo.setUserName(props.getProperty("user.name"));
        propertiesInfo.setUserHome(props.getProperty("user.home"));
        propertiesInfo.setUserDir(props.getProperty("user.dir"));
        return propertiesInfo;
    }

    public static OsInfo buildOsInfo() {
        OsInfo osInfo = new OsInfo();
        osInfo.setDetail(buildOs());
        return osInfo;
    }

    public static SystemInfo buildSystemInfo() {
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setJvm(buildJvmInfo());
        systemInfo.setOs(buildOsInfo());
        systemInfo.setProperties(buildPropertiesInfo());
        return systemInfo;
    }


}
