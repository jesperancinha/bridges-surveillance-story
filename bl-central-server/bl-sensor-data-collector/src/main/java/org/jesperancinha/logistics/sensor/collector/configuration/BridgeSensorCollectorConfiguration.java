package org.jesperancinha.logistics.sensor.collector.configuration;

import org.jesperancinha.logistics.sensor.collector.rabbitmq.BridgeSensorReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "bridge.logistics.bridge.sensor.active",
    matchIfMissing = true)
public class BridgeSensorCollectorConfiguration {

    private static final String BL_BRIDGE_01_SENSOR_EXCHANGE = "bl_bridge_01_sensor_exchange";

    private static final String BL_BRIDGE_01_SENSOR_QUEUE = "bl_bridge_01_sensor_queue";

    @Bean(name ="BridgeQueue")
    Queue queue() {
        return new Queue(BL_BRIDGE_01_SENSOR_QUEUE, true);
    }

    @Bean(name = "BridgeExchange")
    FanoutExchange exchange() {
        return new FanoutExchange(BL_BRIDGE_01_SENSOR_EXCHANGE, true, false);
    }

    @Bean(name = "BridgeBinding")
    Binding binding(
        @Qualifier("BridgeQueue")
            Queue queue,
        @Qualifier("BridgeExchange")
            FanoutExchange exchange) {
        return BindingBuilder.bind(queue)
            .to(exchange);
    }

    @Bean(name = "BridgeContainer")
    SimpleMessageListenerContainer container(
        @Qualifier("BridgeListener")
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
            "localhost"
        );
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
        connectionFactory.setVirtualHost("bl_bridge_01_sensor_vh");
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(BL_BRIDGE_01_SENSOR_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean(name = "BridgeListener")
    MessageListenerAdapter listenerAdapter(BridgeSensorReceiver bridgeSensorReceiver) {
        return new MessageListenerAdapter(bridgeSensorReceiver, "receiveMessage");
    }
}
