package org.jesperancinha.logistics.sensor.collector.configuration;

import org.jesperancinha.logistics.sensor.collector.rabbitmq.BridgeSensorReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "bridge.logistics.bridge.sensor.active",
    matchIfMissing = true)
public class BridgeSensorCollectorConfiguration extends CollectorConfiguration {

    private static final String BL_BRIDGE_01_SENSOR_EXCHANGE = "bl_bridge_01_sensor_exchange";

    private static final String BL_BRIDGE_01_SENSOR_QUEUE = "bl_bridge_01_sensor_queue";

    @Value("${bridge.logistics.bridge.sensor.vhost}")
    private String vHost;

    @Bean(name = "BridgeQueue")
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
        return getSimpleMessageListenerContainer(listenerAdapter, vHost, BL_BRIDGE_01_SENSOR_QUEUE);
    }

    @Bean(name = "BridgeListener")
    MessageListenerAdapter listenerAdapter(BridgeSensorReceiver bridgeSensorReceiver) {
        return new MessageListenerAdapter(bridgeSensorReceiver, "receiveMessage");
    }
}
