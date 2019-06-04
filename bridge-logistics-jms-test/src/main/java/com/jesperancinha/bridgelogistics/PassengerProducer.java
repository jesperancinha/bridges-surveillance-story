package com.jesperancinha.bridgelogistics;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class PassengerProducer {

    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) throws JMSException {

//       ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("defaultUser", "defaultPassword", "tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // JMS messages are sent and received using a Session. We will
        // create here a non-transactional session object. If you want
        // to use transactions you should set the first parameter to 'true'
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic("topic/PasssengerTopic");

        MessageProducer producer = session.createProducer(topic);

        // We will send a small text message saying 'Hello'

        TextMessage message = session.createTextMessage();

        message.setText("HELLO JMS WORLD");
        // Here we are sending the message!
        producer.send(message);
        System.out.println("Sent message '" + message.getText() + "'");

        connection.close();
    }
}