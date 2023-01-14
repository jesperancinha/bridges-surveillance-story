package org.jesperancinha.logistics.sensor.collector.configuration

import org.jesperancinha.logistics.sensor.collector.rabbitmq.TrainSensorReceiver
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = ["bridge.logistics.train.sensor.active"], matchIfMissing = true)
class TrainSensorCollectorConfiguration : CollectorConfiguration() {
    @Value("\${spring.rabbitmq.username}")
    private val username: String? = null

    @Value("\${spring.rabbitmq.password}")
    private val password: String? = null

    @Value("\${bridge.logistics.train.sensor.vhost}")
    private val vHost: String? = null
    @Bean(name = ["TrainQueue"])
    fun queue(): Queue {
        return Queue(BL_TRAIN_01_SENSOR_QUEUE, true)
    }

    @Bean(name = ["TrainExchange"])
    fun exchange(): FanoutExchange {
        return FanoutExchange(BL_TRAIN_01_SENSOR_EXCHANGE, true, false)
    }

    @Bean(name = ["TrainBinding"])
    fun binding(
        @Qualifier("TrainQueue") queue: Queue?,
        @Qualifier("TrainExchange") exchange: FanoutExchange?
    ): Binding {
        return BindingBuilder.bind(queue)
            .to(exchange)
    }

    @Bean(name = ["TrainContainer"])
    fun container(
        @Qualifier("TrainListener") listenerAdapter: MessageListenerAdapter?
    ): SimpleMessageListenerContainer? {
        return getSimpleMessageListenerContainer(listenerAdapter, vHost, BL_TRAIN_01_SENSOR_QUEUE)
    }

    @Bean(name = ["TrainListener"])
    fun listenerAdapter(trainSensorReceiver: TrainSensorReceiver?): MessageListenerAdapter {
        return MessageListenerAdapter(trainSensorReceiver, "receiveMessage")
    }

    companion object {
        private const val BL_TRAIN_01_SENSOR_EXCHANGE = "bl-train-01-sensor-exchange"
        private const val BL_TRAIN_01_SENSOR_QUEUE = "bl-train-01-sensor-queue"
    }
}