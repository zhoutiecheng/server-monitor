package com.zhoutc.monitor.service;

import com.zhoutc.monitor.context.MonitorContext;
import com.zhoutc.zookeeper.client.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-27 18:44
 */
public class MonitorCenterImpl implements MonitorCenter {

    private static final String MIMA = "server.monitor.admin";

    @Override
    public boolean deletePath(String path, String passWord) {
        ZkClient zkClient = MonitorContext.getContext().getZkClient();
        return checkPassWord(passWord) && zkClient.deleteNode(path);
    }

    @Override
    public boolean addPath(String path, String data, String passWord) {
        ZkClient zkClient = MonitorContext.getContext().getZkClient();
        return checkPassWord(passWord) && zkClient.multiLevelCreate(path, data, CreateMode.PERSISTENT);
    }

    @Override
    public boolean updatePath(String path, String data, String passWord) {
        ZkClient zkClient = MonitorContext.getContext().getZkClient();
        return checkPassWord(passWord) && zkClient.updateNodeData(path, data);
    }

    @Override
    public String getPath(String path) {
        ZkClient zkClient = MonitorContext.getContext().getZkClient();
        return zkClient.getNodeData(path);
    }

    private boolean checkPassWord(String passWord) {
        return MIMA.equals(passWord);
    }
}
