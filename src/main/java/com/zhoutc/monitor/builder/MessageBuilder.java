package com.zhoutc.monitor.builder;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-21 16:20
 */
public class MessageBuilder {
    private MessageBuilder(){
    }

    public static String buildWarnMsg(String group , String application,
                                      List<String> configList, List<String> serverList, List<String> missList){
        StringBuilder message = new StringBuilder("");
        message.append("[服务监测系统提醒您: \r\n")
                .append("时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\r\n")
                .append("分组名称: ").append(group).append("  应用名: ").append(application).append("\r\n")
        .append("历史服务器列表: ").append(JSON.toJSON(configList)).append("\r\n")
        .append("当前服务器列表: ").append(JSON.toJSON(serverList)).append("\r\n")
                .append("缺失服务器列表: ").append(JSON.toJSONString(missList)).append("\r\n")
                .append("请登录缺失服务器查看进程情况]");
        return message.toString();
    }
}
