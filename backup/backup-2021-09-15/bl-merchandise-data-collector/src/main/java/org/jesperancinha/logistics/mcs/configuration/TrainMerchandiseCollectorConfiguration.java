package org.jesperancinha.logistics.mcs.configuration;

import org.jesperancinha.logistics.mcs.rabbitmq.TrainMerchandiseReceiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrainMerchandiseCollectorConfiguration extends CollectorConfiguration {

    private static final String BL_TRAIN_01_MERCHANDISE_EXCHANGE = "bl-train-01-merchandise-exchange";

    private static final String BL_TRAIN_01_MERCHANDISE_QUEUE = "bl-train-01-merchandise-queue";

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${bridge.logistics.train.merchandise.sensor.vhost}")
    private String vHost;

    @Bean("TrainMerchandiseQueue")
    Queue queue() {
        return new Queue(BL_TRAIN_01_MERCHANDISE_QUEUE, true);
    }

    @Bean("TrainMerchandiseExchange")
    FanoutExchange exchange() {
        return new FanoutExchange(BL_TRAIN_01_MERCHANDISE_EXCHANGE, true, false);
    }

    @Bean("TrainMerchandiseBinding")
    Binding binding(
        @Qualifier("TrainMerchandiseQueue")
            Queue queue,
        @Qualifier("TrainMerchandiseExchange")
            FanoutExchange exchange) {
        return BindingBuilder.bind(queue)
            .to(exchange);
    }

    @Bean("TrainMerchandiseContainer")
    SimpleMessageListenerContainer container(
        @Qualifier("TrainMerchandiseListener")
        MessageListenerAdapter listenerAdapter) {
        return getSimpleMessageListenerContainer(listenerAdapter, vHost, BL_TRAIN_01_MERCHANDISE_QUEUE);
    }

    @Bean("TrainMerchandiseListener")
    MessageListenerAdapter listenerAdapter(TrainMerchandiseReceiver trainMerchandiseReceiver) {
        return new MessageListenerAdapter(trainMerchandiseReceiver, "receiveMessage");
    }
}
