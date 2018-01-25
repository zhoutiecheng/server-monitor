package com.zhoutc.monitor.utils;


import com.zhoutc.monitor.builder.PathBuilder;
import com.zhoutc.monitor.context.MonitorContext;
import com.zhoutc.monitor.task.LifeMonitor;
import com.zhoutc.monitor.watch.LockWatcher;
import com.zhoutc.zookeeper.client.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-25 15:43
 */
public class LockUtils {
    private static final Logger logger = LoggerFactory.getLogger(LockUtils.class);

    public static boolean getLock() {
        ZkClient zkClient = MonitorContext.getContext().getZkClient();
        boolean add = zkClient.addNodeData(PathBuilder.BASE_PATH + "/lock", IpUtils.getLocalIp(), CreateMode.EPHEMERAL);
        logger.info("LockUtils getLock add={}", add);
        return add;
    }

    public static boolean watchLock() {
        ZkClient zkClient = MonitorContext.getContext().getZkClient();
        Watcher watcher = MonitorContext.getContext().addAndGetWatcher(PathBuilder.BASE_PATH + "/lock", new LockWatcher());
        boolean exists = zkClient.exists(PathBuilder.BASE_PATH + "/lock", watcher);
        logger.info("LockUtils watchLock exists={}", exists);
        return exists;
    }

    public static void runLockMonitor() {
        if(getLock()) {
            logger.info("Monitor获取运行权限");
            MonitorContext.getContext().setStart(true);
            MonitorContext.getContext().setOpen(true);
            LifeMonitor lifeMonitor = new LifeMonitor();
            lifeMonitor.monitor();
        }else{
            MonitorContext.getContext().setStart(false);
            logger.info("Monitor未获取权限，监听其他机器运行");
            watchLock();
        }
    }

}
