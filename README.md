###说明
根据zookeeper实现的服务器监控插件
启动方式：spring配置可用，或者自己手动启动
角色：分为监控者(monitor)和上报者(reporter)两种角色
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
#1.spring配置启动方式：
###1.1 被监控系统reporter配置：
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:server-monitor="http://www.zhoutc.com/schema/monitor"
       xsi:schemaLocation="
          http://www.zhoutc.com/schema/monitor http://www.zhoutc.com/schema/monitor/server-monitor.xsd
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd">
    <server-monitor:reporter id="reporterClient" zk-url="192.168.100.22:2181" group="test" application="serverName" followers="xxxx@xxx.com"   interval="30000" />
</beans>
###1.2 监控系统monitor配置：
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:server-monitor="http://www.zhoutc.com/schema/monitor"
       xsi:schemaLocation="
          http://www.zhoutc.com/schema/monitor http://www.zhoutc.com/schema/monitor/server-monitor.xsd
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd">
    <server-monitor:monitor id="monitorClient" zk-url="192.168.100.22:2181"/>
</beans>
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
#2.手动启动方式：
###2.1 被监控系统reporter配置：
ReporterClient reporter = new ReporterClient("192.168.102.3:2181","group","application","xxx@xx.com"");
reporter.initReporter();
###2.2 监控系统monitor配置：
MonitorClient monitor = new MonitorClient("192.168.102.3:2181");
monitor.initMonitor();