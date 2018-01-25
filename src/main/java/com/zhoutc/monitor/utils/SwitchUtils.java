package com.zhoutc.monitor.utils;

import com.zhoutc.monitor.context.MonitorContext;
import com.zhoutc.monitor.context.ReporterContext;
import com.zhoutc.monitor.enums.SwitchEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-27 13:47
 */
public class SwitchUtils {
    private static final Logger logger = LoggerFactory.getLogger(SwitchUtils.class);

    public static boolean isOpenMonitor(SwitchEnum switchEnum) {
        try {
            switch (switchEnum) {
                case MONITOR:
                    return MonitorContext.getContext().isOpen();

                case REPORTER:
                    return ReporterContext.getContext().isOpen();
            }
        } catch (Exception e) {
            logger.error("isOpenMonitor exception", e);
        }
        return false;
    }
}
