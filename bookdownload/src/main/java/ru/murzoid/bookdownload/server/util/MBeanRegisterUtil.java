package ru.murzoid.bookdownload.server.util;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.jboss.logging.Logger;

import ru.murzoid.bookdownload.server.BasicService;

/**
 * MBeanRegisterUtil.java : MBeanRegisterUtil.java
 *
 * @author ysukhov (ysukhov@extremenetworks.com)
 */
public class MBeanRegisterUtil
{
    private static final Logger logger = Logger
                    .getLogger(MBeanRegisterUtil.class);



    public void registerMBean(BasicService object, ObjectName objectName) throws Exception {
        try {
            logger.info("Registering MBean: " + objectName.getCanonicalName());
            MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            unregisterMBean(objectName);
            platformMBeanServer.registerMBean(object, objectName);
            logger.debug("Starting MBean: " + objectName.getCanonicalName());
            logger.debug("Started MBean: " + objectName.getCanonicalName());
            logger.info("Registered MBean: " + objectName.getCanonicalName());
        } catch (Exception e) {
            logger.error("Problem during registration of "+objectName.getCanonicalName()+" into JMX:" + e, e);
            throw new IllegalStateException("Problem during registration of "+objectName.getCanonicalName()+" into JMX:" + e);
        }
    }

    public void unregisterMBean(BasicService object, ObjectName objectName) throws Exception {
        try {
            logger.debug("Unregistering MBean: " + objectName.getCanonicalName());
            unregisterMBean(objectName);
            logger.trace("Stoping MBean: " + objectName.getCanonicalName());
            logger.trace("Stoped MBean: " + objectName.getCanonicalName());
            logger.debug("Unregistered MBean: " + objectName.getCanonicalName());
        } catch (Exception e) {
            logger.error("Problem during registration of "+this.getClass()+" into JMX:" + e, e);
            throw new IllegalStateException("Problem during unregistration of "+this.getClass()+" into JMX:" + e);
        }
    }

    public void unregisterMBean(ObjectName objectName) throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        if(platformMBeanServer.isRegistered(objectName)){
            platformMBeanServer.unregisterMBean(objectName);
        }
    }
}
