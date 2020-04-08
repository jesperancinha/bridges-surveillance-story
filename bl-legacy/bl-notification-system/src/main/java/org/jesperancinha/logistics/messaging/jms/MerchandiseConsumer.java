package org.jesperancinha.logistics.messaging.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "acknowledgeMode",
    propertyValue = "Auto-acknowledge"), @ActivationConfigProperty(propertyName = "destinationType",
    propertyValue = "javax.jms.Topic"), @ActivationConfigProperty(propertyName = "destination",
    propertyValue = "java:app/jms/MerchandiseTopic") })
public class MerchandiseConsumer implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(MerchandiseConsumer.class);

    @Override
    public void onMessage(Message message) {
        logger.info("Merchandise received!");
    }
}
