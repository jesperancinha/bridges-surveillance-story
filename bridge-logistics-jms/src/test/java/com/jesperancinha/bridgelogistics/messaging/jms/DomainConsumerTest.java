package com.jesperancinha.bridgelogistics.messaging.jms;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DomainConsumerTest {
    @Deployment
    public static JavaArchive createDeployment() {
        System.out.println("BBB");

        return ShrinkWrap.create(JavaArchive.class)
                .addClass(DomainConsumer.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void onMessage() {
        System.out.println("AAAA");
    }
}
