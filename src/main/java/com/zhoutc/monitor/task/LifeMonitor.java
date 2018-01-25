package com.zhoutc.monitor.task;

import com.alibaba.fastjson.JSON;
import com.zhoutc.monitor.builder.PathBuilder;
import com.zhoutc.monitor.context.MonitorContext;
import com.zhoutc.monitor.entity.MonitorInfo;
import com.zhoutc.monitor.enums.NodeLevelEnum;
import com.zhoutc.monitor.executor.SendWarningExecutor;
import com.zhoutc.monitor.recover.MonitorRecover;
import com.zhoutc.monitor.watch.NodeWatcher;
import com.zhoutc.monitor.watch.ServerWatcher;
import com.zhoutc.zookeeper.api.Recoverable;
import com.zhoutc.zookeeper.client.ZkClient;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 监控引用 监控结点存活结点
 * Author:Zhoutc
 * Date:2017-12-21 11:49
 */
public class LifeMonitor {
    private static final Logger logger = LoggerFactory.getLogger(LifeMonitor.class);
    private ZkClient zkClient;


    public LifeMonitor(String zkUrl) {
        try {
            List<Recoverable> recoverPoints = new ArrayList<>();
            recoverPoints.add(new MonitorRecover());
            zkClient = new ZkClient(zkUrl, 5000, recoverPoints);
            MonitorContext.getContext().setZkClient(zkClient);
            SendWarningExecutor.start();
            logger.info("LifeMonitor init success!!!");
        } catch (Exception e) {
            logger.error("zkClient exception!!", e);
        }
    }

    public LifeMonitor() {
        try {
            zkClient = MonitorContext.getContext().getZkClient();
            SendWarningExecutor.start();
            logger.info("LiveReporter init success!!!");
        } catch (Exception e) {
            logger.error("zkClient exception!!", e);
        }
    }

    public void monitor() {
        if (zkClient == null) {
            logger.error("monitor zookeeper connect failed!!");
            return;
        }
        NodeWatcher rootWatch = new NodeWatcher(NodeLevelEnum.GROUP, "");
        List<String> groupList = zkClient.getChildren(PathBuilder.BASE_PATH, MonitorContext.getContext().addAndGetWatcher(PathBuilder.BASE_PATH, rootWatch));
        logger.info("GROUP1 monitor path={} list={}", PathBuilder.BASE_PATH, groupList == null ? null : groupList.size());
        if (groupList == null || groupList.size() == 0) {
            logger.error("monitor path={} group is empty!!", PathBuilder.BASE_PATH);
            return;
        }
        for (String group : groupList) {
            NodeWatcher nodeWatcher = new NodeWatcher(NodeLevelEnum.APPLICATION, group);
            List<String> applicationList = zkClient.getChildren(PathBuilder.BASE_PATH + "/" + group,
                    MonitorContext.getContext().addAndGetWatcher(PathBuilder.BASE_PATH + "/" + group, nodeWatcher));
            logger.info("APPLICATION1 monitor path={} list={}", PathBuilder.BASE_PATH + "/" + group, applicationList == null ? null : applicationList.size());
            if (applicationList == null || applicationList.size() == 0) {
                logger.error("monitor path={} application is empty!!", PathBuilder.BASE_PATH + "/" + group);
                continue;
            }
            for (String application : applicationList) {
                String basePath = PathBuilder.BASE_PATH + "/" + group + "/" + application;
                List<String> servers = zkClient.getChildren(basePath + "/config");
                if (servers == null || "".equals(servers)) {
                    continue;
                }
                Watcher watcher = new ServerWatcher(group, application);
                List<String> list = zkClient.getChildren(basePath + "/server", watcher);
                if(list == null){
                    logger.error("monitor path={} application server is empty!!", PathBuilder.BASE_PATH + "/" + group + "/" + application + "/server");
                    continue;
                }
                MonitorContext.getContext().addAndGetWatcher(basePath + "/server", watcher);
                logger.info("monitor path={} configs={} servers={}", basePath + "/server", JSON.toJSONString(servers), JSON.toJSONString(list));
                servers.removeAll(list);
                if (servers.size() > 0) {
                    MonitorInfo monitorInfo = new MonitorInfo();
                    monitorInfo.setApplication(application);
                    monitorInfo.setServerPath(basePath + "/server");
                    monitorInfo.setConfigPath(basePath + "/config");
                    monitorInfo.setGroup(group);
                    String key = group + "#" + application + "#" + basePath;
                    if(!basePath.startsWith(PathBuilder.BASE_PATH + "/box")){
                        MonitorContext.getContext().getWarnPathMap().put(key, monitorInfo);
                    }
                    logger.info("monitor path={} miss={} servers={}", basePath + "/server",
                            JSON.toJSONString(servers), JSON.toJSONString(list));
                }

            }
        }
    }


}
