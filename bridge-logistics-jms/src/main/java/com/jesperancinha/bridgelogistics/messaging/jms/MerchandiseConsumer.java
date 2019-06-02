package com.jesperancinha.bridgelogistics.messaging.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType",
        propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destinationLookup",
        propertyValue = "java:app/jms/MerchandiseTopic")
})
public class MerchandiseConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        //TODO: Manage merchandise
    }
}
