package com.zhoutc.monitor.executor;

import com.zhoutc.monitor.context.ReporterContext;
import com.zhoutc.monitor.enums.SwitchEnum;
import com.zhoutc.monitor.task.LifeReporter;
import com.zhoutc.monitor.utils.SwitchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-25 11:11
 */
public class ReporterExecutor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ReporterExecutor.class);
    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private LifeReporter lifeReporter;

    public ReporterExecutor(LifeReporter lifeReporter) {
        this.lifeReporter = lifeReporter;
    }

    public static void start() {
        ReporterContext context = ReporterContext.getContext();
        LifeReporter lifeReporter = new LifeReporter(
                context.getZkUrl(), context.getGroup(), context.getApplication(),
                context.getFollower()
        );
        context.setStart(true);
        scheduler.submit(new ReporterExecutor(lifeReporter));
    }

    @Override
    public void run() {
        try {
            boolean reporterSwitch = SwitchUtils.isOpenMonitor(SwitchEnum.REPORTER);
            if (!reporterSwitch) {
                logger.info("ReporterExecutor 上报开关已经关闭 再次启动上报需要打开开关");
            } else {
                lifeReporter.report();
            }
        } catch (Exception e) {
            logger.error("ReporterExecutor run exception", e);
        } finally {
            scheduler.schedule(this, ReporterContext.getContext().getInterval(), TimeUnit.MILLISECONDS);
        }

    }
}
