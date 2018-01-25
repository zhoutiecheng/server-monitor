package com.zhoutc.monitor.watch;

import com.zhoutc.monitor.builder.PathBuilder;
import com.zhoutc.monitor.context.MonitorContext;
import com.zhoutc.monitor.enums.NodeLevelEnum;
import com.zhoutc.zookeeper.client.ZkClient;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-22 9:48
 */
public class NodeWatcher implements Watcher {
    private static final Logger logger = LoggerFactory.getLogger(NodeWatcher.class);
    private NodeLevelEnum level;
    private String group;

    public NodeWatcher(NodeLevelEnum level, String group) {
        this.level = level;
        this.group = group;
    }

    @Override
    public void process(WatchedEvent event) {
        ZkClient zkClient = MonitorContext.getContext().getZkClient();
        try {
            Event.EventType eventType = event.getType();
            Event.KeeperState state = event.getState();
            String path = event.getPath();
            logger.info("结点事件 path={},type={},event={}", path, eventType, state);
            addNodeMonitor(event.getPath(), zkClient);
        } catch (Exception e) {
            logger.error("process exception!!", e);
        } finally {
            switch (level) {
                case GROUP:
                    Watcher watcher = new NodeWatcher(NodeLevelEnum.GROUP, "");
                    List<String> list = zkClient.getChildren(event.getPath(), MonitorContext.getContext().addAndGetWatcher(event.getPath(), watcher));
                    logger.info("GROUP path={} list={} 已经重新watcher", event.getPath(), list == null ? -1 : list.size());
                    break;
                case APPLICATION:
                    Watcher groupWatcher = new NodeWatcher(NodeLevelEnum.APPLICATION, PathBuilder.parseLastPath(event.getPath()));
                    List<String> appList = zkClient.getChildren(event.getPath(), MonitorContext.getContext().addAndGetWatcher(event.getPath(), groupWatcher));
                    logger.info("APPLICATION path={} list={} 已经重新watcher", event.getPath(), appList == null ? -1 : appList.size());
                    break;
            }

        }
    }

    private void addNodeMonitor(String path, ZkClient zkClient) {
        switch (level) {
            case GROUP:
                List<String> list = zkClient.getChildren(path);
                if (list != null || list.size() > 0) {
                    for (String groupPath : list) {
                        boolean exists = MonitorContext.getContext().hasWatch(path + "/" + groupPath);
                        if (!exists) {
                            Watcher watcher = new NodeWatcher(NodeLevelEnum.APPLICATION, groupPath);
                            List<String> applicationList = zkClient.getChildren(path + "/" + groupPath, MonitorContext.getContext().addAndGetWatcher(path + "/" + groupPath, watcher));
                            logger.info("APPLICATION1 path={} list={} 已经重新watcher", path + "/" + groupPath, applicationList == null ? -1 : applicationList.size());
                            List<String> appList = zkClient.getChildren(path + "/" + groupPath);
                            for (String appPath : appList) {
                                List<String> serverList = zkClient.getChildren(path + "/" + groupPath + "/" + appPath + "/server",
                                        MonitorContext.getContext().addAndGetWatcher(path + "/" + groupPath + "/" + appPath + "/server",
                                                new ServerWatcher(groupPath, appPath)));
                                logger.info("SERVER1 path={} list={} 已经重新watcher", path + "/" + groupPath + "/" + appPath + "/server", serverList == null ? -1 : serverList.size());
                            }
                        }
                    }
                }
                break;
            case APPLICATION:
                List<String> applicationList = zkClient.getChildren(path);
                for (String applicationPath : applicationList) {
                    List<String> serverList = zkClient.getChildren(path + "/" + applicationPath + "/server",
                            MonitorContext.getContext().addAndGetWatcher(path + "/" + applicationPath + "/server",
                                    new ServerWatcher(group, applicationPath)));
                    logger.info("SERVER2 path={} list={} 已经重新watcher", path + "/" + applicationPath + "/server", serverList == null ? -1 : serverList.size());
                }

                break;
        }
    }
}
