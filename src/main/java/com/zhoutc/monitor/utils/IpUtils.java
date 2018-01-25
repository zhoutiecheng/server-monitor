package com.zhoutc.monitor.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-21 11:42
 */
public class IpUtils {
    private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);
    private static final String IP_REGEX = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    private IpUtils() {
    }

    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();

        } catch (Exception e) {
            logger.error("getLocalIp exception!!", e);
        }

        return "";
    }

    public static boolean checkIp(String ipString) {
        return ipString.matches(IP_REGEX);
    }
}
