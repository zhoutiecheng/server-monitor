package com.zhoutc.monitor.client;

import com.zhoutc.monitor.context.MonitorContext;
import com.zhoutc.monitor.recover.MonitorRecover;
import com.zhoutc.monitor.utils.LockUtils;
import com.zhoutc.monitor.utils.PrintUtils;
import com.zhoutc.zookeeper.api.Recoverable;
import com.zhoutc.zookeeper.client.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-26 16:05
 */
public class MonitorClient implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(MonitorClient.class);
    private String zkUrl;

    public MonitorClient(String zkUrl) {
        this.zkUrl = zkUrl;
    }

    public String getZkUrl() {
        return zkUrl;
    }

    public void setZkUrl(String zkUrl) {
        this.zkUrl = zkUrl;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            initMonitor();
        }catch (Exception e){
            logger.error("onApplicationEvent exception",e);
        }
    }

    public void initMonitor() throws Exception {
        logger.info("----------------------------Server-monitor Init Start-----------------------------------");
        PrintUtils.printNoBug();
        MonitorContext context = MonitorContext.getContext();
        List<Recoverable> recoverPoints = new ArrayList<>();
        recoverPoints.add(new MonitorRecover());
        ZkClient zkClient = new ZkClient(zkUrl, 5000, recoverPoints);
        context.setZkUrl(zkUrl);
        context.setZkClient(zkClient);
        LockUtils.runLockMonitor();
        logger.info("--------------------------Server-monitor Init Over--------------------------------------");
    }
}
