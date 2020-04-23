package org.jesperancinha.logistics.messaging.jms;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.annotations.Test;

public class DomainConsumerTest extends Arquillian {
    ;

    @Deployment
    public static JavaArchive createDeployment() {
        System.out.println("Deploying...");
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(DomainConsumer.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void testOnMessage() {
        System.out.println("WOW!!");

    }
}
