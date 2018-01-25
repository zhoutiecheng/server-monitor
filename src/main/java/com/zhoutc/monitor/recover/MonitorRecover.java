package com.zhoutc.monitor.recover;


import com.zhoutc.zookeeper.api.Recoverable;
import com.zhoutc.monitor.utils.LockUtils;
import com.zhoutc.monitor.utils.PrintUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Author:Zhoutc
 * Date:2018-1-24 17:06
 */
public class MonitorRecover implements Recoverable {
    private static final Logger logger = LoggerFactory.getLogger(MonitorRecover.class);
    @Override
    public boolean recovery() {
        logger.info("----------------------------Server-monitor reInit Start-----------------------------------");
        PrintUtils.printNoBug();
        LockUtils.runLockMonitor();
        logger.info("--------------------------Server-monitor reInit Over--------------------------------------");
        return true;
    }

    @Override
    public String getName() {
        return "MonitorRecover";
    }
}
