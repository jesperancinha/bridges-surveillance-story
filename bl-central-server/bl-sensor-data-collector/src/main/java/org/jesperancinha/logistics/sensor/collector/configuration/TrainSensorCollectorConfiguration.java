package org.jesperancinha.logistics.sensor.collector.configuration;

import org.jesperancinha.logistics.sensor.collector.rabbitmq.TrainSensorReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "bridge.logistics.train.sensor.active",
    matchIfMissing = true)
public class TrainSensorCollectorConfiguration {

    private static final String BL_TRAIN_01_SENSOR_EXCHANGE = "bl_train_01_sensor_exchange";

    private static final String BL_TRAIN_01_SENSOR_QUEUE = "bl_train_01_sensor_queue";

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
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
        @Qualifier("TrainListener")
            MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(BL_TRAIN_01_SENSOR_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean(name = "TrainListener")
    MessageListenerAdapter listenerAdapter(TrainSensorReceiver trainSensorReceiver) {
        return new MessageListenerAdapter(trainSensorReceiver, "receiveMessage");
    }
}
