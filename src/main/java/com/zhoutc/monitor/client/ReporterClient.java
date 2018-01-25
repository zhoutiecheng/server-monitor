package com.zhoutc.monitor.client;

import com.zhoutc.monitor.context.ReporterContext;
import com.zhoutc.monitor.executor.ReporterExecutor;
import com.zhoutc.monitor.utils.PrintUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-26 15:48
 */
public class ReporterClient implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ReporterClient.class);
    private String zkUrl;
    private String application;
    private String group;
    private String follower;
    private long interval;

    public ReporterClient(String zkUrl, String group,
                          String application, String follower, long interval) {
        this.zkUrl = zkUrl;
        this.application = application;
        this.group = group;
        this.follower = follower;
        this.interval = interval;
    }

    public ReporterClient(String zkUrl, String application,
                          String group, String follower) {
        this.zkUrl = zkUrl;
        this.application = application;
        this.group = group;
        this.follower = follower;
        this.interval = 30000;
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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            initReporter();
        }catch (Exception e){
            logger.error("onApplicationEvent exception",e);
        }
    }

    public void initReporter() throws Exception {
        logger.info("----------------------------Server-reporter Init Start-----------------------------------");
        PrintUtils.printNoBug();
        ReporterContext context = ReporterContext.getContext();
        context.setInterval(interval);
        context.setGroup(group);
        context.setApplication(application);
        context.setZkUrl(zkUrl);
        context.setFollower(follower);
        context.setOpen(true);
        if(!ReporterContext.getContext().isStart()) {
            ReporterExecutor.start();
        }
        logger.info("----------------------------Server-reporter Init Over-----------------------------------");
    }
}
