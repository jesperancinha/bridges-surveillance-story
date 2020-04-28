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
public class VehicleMerchandiseCollectorConfiguration extends CollectorConfiguration {

    private static final String BL_VEHICLE_01_MERCHANDISE_EXCHANGE = "bl_vehicle_01_merchandise_exchange";

    private static final String BL_VEHICLE_01_MERCHANDISE_QUEUE = "bl_vehicle_01_merchandise_queue";

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${bridge.logistics.vehicle.merchandise.sensor.vhost}")
    private String vHost;

    @Bean("VehicleMerchandiseQueue")
    Queue queue() {
        return new Queue(BL_VEHICLE_01_MERCHANDISE_QUEUE, true);
    }

    @Bean("VehicleMerchandiseExchange")
    FanoutExchange exchange() {
        return new FanoutExchange(BL_VEHICLE_01_MERCHANDISE_EXCHANGE, true, false);
    }

    @Bean("VehicleMerchandiseBinding")
    Binding binding(
        @Qualifier("VehicleMerchandiseQueue")
            Queue queue,
        @Qualifier("VehicleMerchandiseExchange")
            FanoutExchange exchange) {
        return BindingBuilder.bind(queue)
            .to(exchange);
    }

    @Bean("VehicleMerchandiseContainer")
    SimpleMessageListenerContainer container(
        @Qualifier("VehicleMerchandiseListener")
            MessageListenerAdapter listenerAdapter) {
        return getSimpleMessageListenerContainer(listenerAdapter, vHost, BL_VEHICLE_01_MERCHANDISE_QUEUE);
    }

    @Bean("VehicleMerchandiseListener")
    MessageListenerAdapter listenerAdapter(TrainMerchandiseReceiver trainMerchandiseReceiver) {
        return new MessageListenerAdapter(trainMerchandiseReceiver, "receiveMessage");
    }
}
