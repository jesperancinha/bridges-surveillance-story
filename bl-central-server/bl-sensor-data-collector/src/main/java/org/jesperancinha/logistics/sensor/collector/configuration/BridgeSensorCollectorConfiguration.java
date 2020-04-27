package org.jesperancinha.logistics.sensor.collector.configuration;

import org.jesperancinha.logistics.sensor.collector.rabbitmq.BridgeSensorReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BridgeSensorCollectorConfiguration {

    private static final String BL_MERCHANDISE_EXCHANGE = "bl_merchandise_exchange";

    private static final String BL_MERCHANDISE_QUEUE = "bl_merchandise_queue";

    @Bean
    Queue queue() {
        return new Queue(BL_MERCHANDISE_QUEUE, true);
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(BL_MERCHANDISE_EXCHANGE, true, false);
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue)
            .to(exchange);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(BL_MERCHANDISE_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(BridgeSensorReceiver bridgeSensorReceiver) {
        return new MessageListenerAdapter(bridgeSensorReceiver, "receiveMessage");
    }
}
