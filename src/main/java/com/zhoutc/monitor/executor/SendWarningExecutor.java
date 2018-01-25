package com.zhoutc.monitor.executor;

import com.alibaba.fastjson.JSON;
import com.zhoutc.monitor.builder.MessageBuilder;
import com.zhoutc.monitor.builder.PathBuilder;
import com.zhoutc.monitor.context.MonitorContext;
import com.zhoutc.monitor.entity.MonitorInfo;
import com.zhoutc.monitor.enums.SwitchEnum;
import com.zhoutc.monitor.utils.CloneUtil;
import com.zhoutc.monitor.utils.SwitchUtils;
import com.zhoutc.zookeeper.client.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-21 16:06
 */
public class SendWarningExecutor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SendWarningExecutor.class);
    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public SendWarningExecutor() {
    }

    public static void start() {
        scheduler.submit(new SendWarningExecutor());
    }

    @Override
    public void run() {
        try {
             boolean monitorSwitch = SwitchUtils.isOpenMonitor(SwitchEnum.MONITOR);
            if (!monitorSwitch) {
                logger.info("SendWarningExecutor 监控开关已经关闭 再次启动监控需要打开开关");
                MonitorContext.getContext().getWarnPathMap().clear();
            } else {
                Map<String,MonitorInfo> monitorInfoMap = MonitorContext.getContext().getWarnPathMap();
                ZkClient zkClient = MonitorContext.getContext().getZkClient();
                if(!zkClient.isRetrying()){
                    zkClient.getChildren(PathBuilder.BASE_PATH);
                }
                if (monitorInfoMap != null && monitorInfoMap.size() > 0) {
                    Iterator<Map.Entry<String, MonitorInfo>> iterable = monitorInfoMap.entrySet().iterator();
                    while (iterable.hasNext()) {
                        Map.Entry<String, MonitorInfo> warnEntry = iterable.next();
                        MonitorInfo monitorInfo = warnEntry.getValue();
                        logger.info("处理警告 MonitorInfo={}", JSON.toJSONString(monitorInfo));
                        List<String> configList = zkClient.getChildren(monitorInfo.getConfigPath());
                        List<String> serverList = zkClient.getChildren(monitorInfo.getServerPath());
                        List<String> copyConfig = CloneUtil.cloneList(configList);
                        List<String> copyServer = CloneUtil.cloneList(serverList);
                        logger.info("监控服务器watcher获取服务器列表,application={},group={},configList={},serverList={}",
                                monitorInfo.getApplication(), monitorInfo.getGroup(), JSON.toJSONString(configList), JSON.toJSONString(serverList));
                        //求差集
                        configList.removeAll(serverList);
                        if (configList.size() > 0) {
                            logger.info("发送警告信息 缺失服务节点 group={},application={},missServers={} configServers={} servers={}", monitorInfo.getGroup(), monitorInfo.getApplication(),
                                    JSON.toJSONString(configList), JSON.toJSONString(copyConfig), JSON.toJSONString(copyServer));
                            String followers = zkClient.getNodeData(monitorInfo.getConfigPath());
                            if (followers == null || "".equals(followers)) {
                                logger.error("监控服务器watcher 邮件发送者为空,application={},group={}",
                                        monitorInfo.getApplication(), monitorInfo.getGroup());
                            } else {
                                List<String> toList = Arrays.asList(followers.split(","));
                                String message = MessageBuilder.buildWarnMsg(
                                        monitorInfo.getGroup(), monitorInfo.getApplication(),
                                        copyConfig, copyServer, configList);
                                //@TODO 实现具体告警消息发送
                                logger.info("监控服务器watcher 发送邮件结果,application={},group={},message={},toList={}",
                                        monitorInfo.getApplication(), monitorInfo.getGroup(), message, JSON.toJSONString(toList));
                            }
                        } else{
                            iterable.remove();
                            logger.info("告警解除 group={},application={}", monitorInfo.getGroup(), monitorInfo.getApplication());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("发送警告异常！！", e);
        } finally {
            scheduler.schedule(this, 30, TimeUnit.SECONDS);
            Map<String, MonitorInfo> map = MonitorContext.getContext().getWarnPathMap();
            if(!CollectionUtils.isEmpty(map)) {
                logger.info("告警进程一次处理完毕, warnPathSet={}", JSON.toJSONString(map));
            }
        }
    }
}
