package com.zhoutc.monitor.init;

import com.zhoutc.monitor.client.MonitorClient;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Description:
 * Author:Zhoutc
 * Date:2017-12-25 11:00
 */
public class MonitorDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext context) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(MonitorClient.class);
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, element.getAttribute("zk-url"));
        String id = element.getAttribute("id");
        context.getRegistry().registerBeanDefinition(id, beanDefinition);
        return beanDefinition;
    }
}
