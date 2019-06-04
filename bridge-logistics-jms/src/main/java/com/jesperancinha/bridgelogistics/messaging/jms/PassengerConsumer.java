package com.jesperancinha.bridgelogistics.messaging.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "topic/PasssengerTopic")
})
public class PassengerConsumer implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(PassengerConsumer.class);

    public PassengerConsumer() {
        super();
    }

    @Override
    public void onMessage(Message message) {
        logger.info("Passenger received!");
    }
}
