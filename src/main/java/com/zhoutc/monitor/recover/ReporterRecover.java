package com.zhoutc.monitor.recover;

import com.zhoutc.monitor.context.ReporterContext;
import com.zhoutc.zookeeper.api.Recoverable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Author:Zhoutc
 * Date:2018-1-24 15:05
 */
public class ReporterRecover implements Recoverable {
    private static final Logger logger = LoggerFactory.getLogger(ReporterRecover.class);
    @Override
    public boolean recovery() {
        ReporterContext.getContext().setInit(false);
        logger.info("ReporterRecover 执行结束！");
        return true;
    }

    @Override
    public String getName() {
        return "ReporterRecover";
    }
}
