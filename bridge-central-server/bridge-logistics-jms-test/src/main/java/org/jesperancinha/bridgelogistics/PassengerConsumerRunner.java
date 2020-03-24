package org.jesperancinha.bridgelogistics;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class PassengerConsumerRunner {

    private String topicName = "topic/PasssengerTopic";
    private String initialContextFactory = ""
            + "org.apache.activemq.jndi.ActiveMQInitialContextFactory";
    private String connectionString = "tcp://localhost:61616";

    private boolean messageReceived = false;


    public static void main(String[] args) {
        PassengerConsumerRunner subscriber = new PassengerConsumerRunner();
        subscriber.subscribeWithTopicLookup();
    }

    public void subscribeWithTopicLookup() {

        Properties properties = new Properties();
        TopicConnection topicConnection = null;
        properties.put("java.naming.factory.initial", initialContextFactory);
        properties.put("connectionfactory.QueueConnectionFactory",
                connectionString);
        properties.put("topic." + topicName, topicName);
        try {
            InitialContext ctx = new InitialContext(properties);
            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) ctx
                    .lookup("QueueConnectionFactory");
            topicConnection = topicConnectionFactory.createTopicConnection();
            System.out
                    .println("Create Topic Connection for Topic " + topicName);

            while (!messageReceived) {
                try {
                    TopicSession topicSession = topicConnection
                            .createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

                    Topic topic = (Topic) ctx.lookup(topicName);
                    // start the connection
                    topicConnection.start();

                    // create a topic subscriber
                    javax.jms.TopicSubscriber topicSubscriber = topicSession
                            .createSubscriber(topic);

                    TestMessageListener messageListener = new TestMessageListener();
                    topicSubscriber.setMessageListener(messageListener);

                    Thread.sleep(5000);
                    topicSubscriber.close();
                    topicSession.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                } catch (NamingException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (NamingException e) {
            throw new RuntimeException("Error in initial context lookup", e);
        } catch (JMSException e) {
            throw new RuntimeException("Error in JMS operations", e);
        } finally {
            if (topicConnection != null) {
                try {
                    topicConnection.close();
                } catch (JMSException e) {
                    throw new RuntimeException(
                            "Error in closing topic connection", e);
                }
            }
        }
    }

    public class TestMessageListener implements MessageListener {
        public void onMessage(Message message) {
            try {
                System.out.println("Got the Message : "
                        + ((TextMessage) message).getText());
                // messageReceived = true;
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}