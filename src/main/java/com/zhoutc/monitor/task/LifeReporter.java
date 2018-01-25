package com.zhoutc.monitor.task;


import com.alibaba.fastjson.JSON;
import com.zhoutc.monitor.builder.PathBuilder;
import com.zhoutc.monitor.builder.SystemBuider;
import com.zhoutc.monitor.context.ReporterContext;
import com.zhoutc.monitor.recover.ReporterRecover;
import com.zhoutc.zookeeper.api.Recoverable;
import com.zhoutc.zookeeper.client.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 系统引用 启动注册信息到zookeeper
 * Author:Zhoutc
 * Date:2017-12-20 10:15
 */
public class LifeReporter {
    private static final Logger logger = LoggerFactory.getLogger(LifeReporter.class);
    private ZkClient zkClient;
    private String application;
    private String group;
    private String followers;

    public LifeReporter(String zkUrl, String group, String application, String followers) {
        try {
            this.application = application;
            this.group = group;
            this.followers = followers;
            List<Recoverable> recoverPoints = new ArrayList<>();
            recoverPoints.add(new ReporterRecover());
            zkClient = new ZkClient(zkUrl, 5000, recoverPoints);
            ReporterContext.getContext().setZkClient(zkClient);
            logger.info("LiveReporter init success!!!");
        } catch (Exception e) {
            logger.error("zkClient exception", e);
        }
    }

    public void init() {
        try {
            if (zkClient == null) {
                logger.error("report zookeeper connect failed!!");
                return;
            }

            //写入临时结点
            boolean register = false;
            int retry = 0;
            String serverPath = PathBuilder.getServerPath(group, application);
            while (!register && retry <= 3) {
                retry++;
                register = zkClient.multiLevelCreate(serverPath,
                        JSON.toJSONString(SystemBuider.buildSystemInfo()),
                        CreateMode.EPHEMERAL);
                logger.info("register zookeeper add serverPath={},retry={},result={}", serverPath, retry, register);
            }

            //写入持久化结点
            retry = 0;
            register = false;
            String configPath = PathBuilder.getConfigPath(group, application);
            while (!register && retry <= 3) {
                retry++;
                register = zkClient.multiLevelCreate(configPath,
                        "",
                        CreateMode.PERSISTENT);
                logger.info("register zookeeper configPath={},retry={},result={}", configPath, retry, register);
            }

            //写入告警人信息
            retry = 0;
            register = false;
            String basePath = PathBuilder.getBasePath(group, application) + "/config";
            while (!register && retry <= 3) {
                retry++;
                register = zkClient.updateNodeData(basePath,
                        followers);
                logger.info("register zookeeper basePath={},retry={},result={}", basePath, retry, register);
            }
            ReporterContext.getContext().setInit(true);
        } catch (Exception e) {
            logger.error("register zookeeper exception", e);
        }

    }

    public void update() {
        //写入临时结点
        boolean register = false;
        int retry = 0;
        String serverPath = PathBuilder.getServerPath(group, application);
        while (!register && retry <= 3) {
            retry++;
            register = zkClient.updateNodeData(serverPath,
                    JSON.toJSONString(SystemBuider.buildSystemInfo()));
            if(!register) {
                logger.info("上报zookeeper失败 register zookeeper update serverPath={},retry={},result={}", serverPath, retry, register);
            }
        }
    }

    public void report() {
        if (!ReporterContext.getContext().isInit()) {
            logger.info("LifeReporter 开始初始化结点信息！！");
            init();
            logger.info("LifeReporter 结束初始化结点信息！！");
        } else {
            update();
        }
    }

}
