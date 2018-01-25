package com.zhoutc.monitor.watch;

import com.alibaba.fastjson.JSON;
import com.zhoutc.monitor.utils.LockUtils;
import com.zhoutc.monitor.utils.PrintUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-27 10:56
 */
public class LockWatcher implements Watcher {
    private static final Logger logger = LoggerFactory.getLogger(LockWatcher.class);

    @Override
    public void process(WatchedEvent event) {
        logger.info("LockWatcher WatchedEvent={}", JSON.toJSONString(event));
        PrintUtils.printNoBug();
        LockUtils.runLockMonitor();
    }
}
