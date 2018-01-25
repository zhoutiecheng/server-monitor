package com.zhoutc.monitor.test;

import com.zhoutc.monitor.builder.PathBuilder;
import com.zhoutc.monitor.client.MonitorClient;
import com.zhoutc.zookeeper.client.ZkClient;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-22 15:56
 */
@RunWith(JMockit.class)
public class LifeMonitorTest {

    @Test
    public void testMonitor() throws Exception{
        MonitorClient monitorClient = new MonitorClient("192.168.103.222:2181");
        monitorClient.initMonitor();
        Thread.sleep(5000000000l);
    }

    @Test
    public void test(){
        ZkClient zkClient = new ZkClient("192.168.103.222:2181", 5000, null);
        List<String> groupList = zkClient.getChildren(PathBuilder.BASE_PATH);
        System.out.println(groupList.toString());
    }
}
