package com.zhoutc.monitor.init;

import com.zhoutc.monitor.client.ReporterClient;
import org.apache.commons.lang.StringUtils;
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
public class ReporterDefinitionParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext context) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(ReporterClient.class);
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, element.getAttribute("zk-url"));
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(1, element.getAttribute("group"));
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(2, element.getAttribute("application"));
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(3, element.getAttribute("followers"));
        String intervalString = element.getAttribute("interval");
        if(StringUtils.isNotEmpty(intervalString)){
            beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(4, element.getAttribute("interval"));
        }
        context.getRegistry().registerBeanDefinition(element.getAttribute("id"), beanDefinition);
        return beanDefinition;
    }


}
