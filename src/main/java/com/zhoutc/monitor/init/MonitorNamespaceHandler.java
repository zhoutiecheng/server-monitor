package com.zhoutc.monitor.init;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-25 11:00
 */
public class MonitorNamespaceHandler extends NamespaceHandlerSupport {


    @Override
    public void init() {
        registerBeanDefinitionParser("reporter", new ReporterDefinitionParser());
        registerBeanDefinitionParser("monitor", new MonitorDefinitionParser());
    }
}
