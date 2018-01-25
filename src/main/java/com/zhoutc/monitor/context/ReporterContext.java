package com.zhoutc.monitor.context;

import com.zhoutc.zookeeper.client.ZkClient;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-22 17:12
 */
public class ReporterContext {
    private static ReporterContext context;
    private ZkClient zkClient;
    private String zkUrl;
    private String application;
    private String group;
    private String follower;
    private long interval;
    private volatile boolean open;
    private volatile boolean start = false;
    private volatile boolean init = false;

    private ReporterContext() {

    }

    public static ReporterContext getContext() {
        if (context == null) {
            synchronized (ReporterContext.class) {
                if (context == null)
                    context = new ReporterContext();
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

    public String getZkUrl() {
        return zkUrl;
    }

    public void setZkUrl(String zkUrl) {
        this.zkUrl = zkUrl;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}

