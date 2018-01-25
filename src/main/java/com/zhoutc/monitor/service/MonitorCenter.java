package com.zhoutc.monitor.service;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-27 18:44
 */
public interface MonitorCenter {

    boolean deletePath(String path, String passWord);

    boolean addPath(String path, String data, String passWord);

    boolean updatePath(String path, String data, String passWord);

    String getPath(String path);

}
