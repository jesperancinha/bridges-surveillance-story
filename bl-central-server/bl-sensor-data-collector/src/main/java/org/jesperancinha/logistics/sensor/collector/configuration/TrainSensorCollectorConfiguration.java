package org.jesperancinha.logistics.sensor.collector.configuration;

import org.jesperancinha.logistics.sensor.collector.rabbitmq.TrainSensorReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "bridge.logistics.train.sensor.active",
    matchIfMissing = true)
public class TrainSensorCollectorConfiguration extends CollectorConfiguration{

    private static final String BL_TRAIN_01_SENSOR_EXCHANGE = "bl_train_01_sensor_exchange";

    private static final String BL_TRAIN_01_SENSOR_QUEUE = "bl_train_01_sensor_queue";

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${bridge.logistics.train.sensor.vhost}")
    private String vHost;

    @Bean(name = "TrainQueue")
    Queue queue() {
        return new Queue(BL_TRAIN_01_SENSOR_QUEUE, true);
    }

    @Bean(name = "TrainExchange")
    FanoutExchange exchange() {
        return new FanoutExchange(BL_TRAIN_01_SENSOR_EXCHANGE, true, false);
    }

    @Bean(name = "TrainBinding")
    Binding binding(
        @Qualifier("TrainQueue")
            Queue queue,
        @Qualifier("TrainExchange")
            FanoutExchange exchange) {
        return BindingBuilder.bind(queue)
            .to(exchange);
    }

    @Bean(name = "TrainContainer")
    SimpleMessageListenerContainer container(
        @Qualifier("TrainListener")
            MessageListenerAdapter listenerAdapter) {
        return getSimpleMessageListenerContainer(
            listenerAdapter, vHost, BL_TRAIN_01_SENSOR_QUEUE);
    }

    @Bean(name = "TrainListener")
    MessageListenerAdapter listenerAdapter(TrainSensorReceiver trainSensorReceiver) {
        return new MessageListenerAdapter(trainSensorReceiver, "receiveMessage");
    }
}
