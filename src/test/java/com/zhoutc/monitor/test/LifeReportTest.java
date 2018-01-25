package com.zhoutc.monitor.test;

import com.zhoutc.monitor.client.ReporterClient;
import com.zhoutc.monitor.enums.NodeLevelEnum;
import com.zhoutc.monitor.task.LifeReporter;
import com.zhoutc.monitor.watch.NodeWatcher;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 18:59
 */
@RunWith(JMockit.class)
public class LifeReportTest {


    @Test
    public void testReport() throws Exception{
        ReporterClient reporterClient = new ReporterClient("192.168.103.222:2181", "server",
                "test", "xxx@xxxx.com");
        reporterClient.initReporter();
        System.out.println("系统启动完毕！！！");
        Thread.sleep(5000000000l);
        System.out.println("系统已经挂掉！！！");
    }

    @Test
    public void testReport3() {
        LifeReporter liveReporter = new LifeReporter("192.168.100.222:2181", "test", "push", "xxx@xxx.com");
        liveReporter.report();
        System.out.println("系统启动完毕！！！");
        System.out.println("系统已经挂掉！！！");
    }


    @Test
    public void testReport2() {
        LifeReporter liveReporter = new LifeReporter("192.168.100.222:2181", "test", "feature", "xxx@xxx.com");
        liveReporter.report();
        System.out.println("系统启动完毕！！！");
        System.out.println("系统已经挂掉！！！");
    }

    @Test
    public void test(){
        Map<String,NodeWatcher> map = new HashMap<>();
        NodeWatcher x = new NodeWatcher(NodeLevelEnum.APPLICATION,"");
        NodeWatcher y = new NodeWatcher(NodeLevelEnum.APPLICATION,"");
        System.out.println("x:" + x);
        System.out.println("y:" + y);
        NodeWatcher z = map.putIfAbsent("1",x);
        System.out.println("z:" + z);
        NodeWatcher w =  map.putIfAbsent("1",y);
        System.out.println("w:" + w);
    }

}
