package com.zhoutc.monitor.watch;

import com.alibaba.fastjson.JSON;
import com.zhoutc.monitor.builder.MessageBuilder;
import com.zhoutc.monitor.builder.PathBuilder;
import com.zhoutc.monitor.context.MonitorContext;
import com.zhoutc.monitor.entity.MonitorInfo;
import com.zhoutc.monitor.utils.CloneUtil;
import com.zhoutc.zookeeper.client.ZkClient;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Description: 暗中观察服务器 ###|_^|###
 * Author:Zhoutc
 * Date:2017-12-21 14:06
 */
public class ServerWatcher implements Watcher {
    private static final Logger logger = LoggerFactory.getLogger(ServerWatcher.class);
    private String application;
    private String group;

    public ServerWatcher(String group, String application) {
        this.application = application;
        this.group = group;
    }

    @Override
    public void process(WatchedEvent event) {
        ZkClient zkClient = MonitorContext.getContext().getZkClient();
        String basePath = PathBuilder.deleteLastPath(event.getPath());
        String configPath = basePath + "/config";
        String serverPath = event.getPath();
        logger.error("监控服务器watcher事件通知,application={},group={}，path={}, state={}, type={}",
                application, group, event.getPath(), event.getState(), event.getType());
        try {
            List<String> configList = zkClient.getChildren(configPath);
            List<String> serverList = zkClient.getChildren(serverPath);
            List<String> copyConfig = CloneUtil.cloneList(configList);
            List<String> copyServer = CloneUtil.cloneList(serverList);
            if (configList == null || configList.size() == 0) {
                logger.error("监控服务器watcher异常，application={},group={},configList.size={},serverList.size={}",
                        application, group, configList == null ? -1 : configList.size(),
                        serverList == null ? -1 : serverList.size());
                reWatch(serverPath);
                return;
            }
            logger.info("监控服务器watcher获取服务器列表,application={},group={},configList={},serverList={}",
                    application, group, JSON.toJSONString(configList), JSON.toJSONString(serverList));
            //求差集
            configList.removeAll(serverList);
            if (configList.size() > 0) {
                //有服务消失
                String followers = zkClient.getNodeData(configPath);
                if (followers == null || "".equals(followers)) {
                    logger.error("监控服务器watcher 邮件发送者为空,application={},group={}",
                            application, group);
                } else {
                    List<String> toList = Arrays.asList(followers.split(","));
                    String message = MessageBuilder.buildWarnMsg(
                            group, application, copyConfig, copyServer, configList);
                    logger.info("监控服务器watcher 服务器消失 ,application={},group={},path={},missServers={},configServers={},servers={}",
                            application, group, serverPath, JSON.toJSONString(configList), JSON.toJSONString(copyConfig), JSON.toJSONString(copyServer));
                    MonitorInfo monitorInfo = new MonitorInfo();
                    monitorInfo.setApplication(application);
                    monitorInfo.setServerPath(event.getPath());
                    monitorInfo.setConfigPath(configPath);
                    monitorInfo.setGroup(group);
                    String key = group + "#" + application + "#" + basePath;
                    if(!basePath.startsWith(PathBuilder.BASE_PATH + "/box")) {
                        MonitorContext.getContext().getWarnPathMap().put(key, monitorInfo);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("监控服务器watcher异常,application={},group={}", application, group, e);
        } finally {
            reWatch(serverPath);
            logger.info("服务器watcher重新监听,application={},group={}", application, group);
        }
        logger.info("监控服务器watcher处理结束!!!,application={},group={}", application, group);
    }

    public void reWatch(String path) {
        ZkClient zkClient = MonitorContext.getContext().getZkClient();
        zkClient.getChildren(path, new ServerWatcher(group, application));
    }
}
