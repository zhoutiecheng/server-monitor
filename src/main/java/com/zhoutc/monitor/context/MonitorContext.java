package com.zhoutc.monitor.context;

import com.zhoutc.monitor.entity.MonitorInfo;
import com.zhoutc.zookeeper.client.ZkClient;
import org.apache.zookeeper.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-21 16:09
 */
public class MonitorContext {
    private static MonitorContext context;
    private String zkUrl;
    private volatile boolean start;
    private ZkClient zkClient;
    private volatile boolean open;
    private Map<String,MonitorInfo> warnPathMap = new HashMap<>();
    private Map<String, Watcher> monitorMap = new ConcurrentHashMap<>();
    private MonitorContext() {

    }

    public static MonitorContext getContext() {
        if (context == null) {
            synchronized (MonitorContext.class) {
                if (context == null)
                    context = new MonitorContext();
            }
        }
        return context;
    }

    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    public Map<String,MonitorInfo> getWarnPathMap() {
        return warnPathMap;
    }

    public void setWarnPathMap(Map<String,MonitorInfo> warnPathMap) {
        this.warnPathMap = warnPathMap;
    }

    public Watcher addAndGetWatcher(String path, Watcher watcher) {
        Watcher cacheWatcher = monitorMap.putIfAbsent(path, watcher);
        return cacheWatcher == null ? watcher : cacheWatcher;
    }

    public boolean hasWatch(String path) {
        return monitorMap.get(path) != null;
    }

    public String getZkUrl() {
        return zkUrl;
    }

    public void setZkUrl(String zkUrl) {
        this.zkUrl = zkUrl;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
