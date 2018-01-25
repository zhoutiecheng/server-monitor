package com.zhoutc.monitor.builder;

import com.zhoutc.monitor.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-20 18:03
 */
public class PathBuilder {
    private static final Logger logger = LoggerFactory.getLogger(PathBuilder.class);
    public static final String BASE_PATH = "/monitor";

    private PathBuilder() {
    }

    public static String getServerPath(String group, String application) {
        try {
            return BASE_PATH + "/" + group + "/" + application + "/server/" + IpUtils.getLocalIp();
        } catch (Exception e) {
            logger.error("getPath exception!!", e);
        }
        return null;
    }

    public static String getConfigPath(String group, String application) {
        try {
            return BASE_PATH + "/" + group + "/" + application + "/config/" + IpUtils.getLocalIp();
        } catch (Exception e) {
            logger.error("getPath exception!!", e);
        }
        return null;
    }

    public static String getBasePath(String group, String application) {
        try {
            return BASE_PATH + "/" + group + "/" + application ;
        } catch (Exception e) {
            logger.error("getPath exception!!", e);
        }
        return null;
    }

    public static String parseLastPath(String pathString) {
      try {
          String[] path = pathString.split("/");
          if(path == null || path.length == 0){
              return null;
          }
          return path[path.length-1];
      }catch (Exception e){
          logger.error("parseGroup exception!!", e);
      }

      return null;
    }

    public static String deleteLastPath(String pathString) {
        try {
            String lastPath = parseLastPath(pathString);
            if(lastPath == null){
                return null;
            }
            return pathString.substring(0,pathString.length() - lastPath.length() -1);
        }catch (Exception e){
            logger.error("parseGroup exception!!", e);
        }

        return null;
    }
}
