package org.jesperancinha.logistics.mcs.configuration

import org.jesperancinha.logistics.mcs.rabbitmq.TrainMerchandiseReceiver
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TrainMerchandiseCollectorConfiguration : CollectorConfiguration() {
    @Value("\${spring.rabbitmq.username}")
    private val username: String? = null

    @Value("\${spring.rabbitmq.password}")
    private val password: String? = null

    @Value("\${bridge.logistics.train.merchandise.sensor.vhost}")
    private val vHost: String? = null
    @Bean("TrainMerchandiseQueue")
    fun queue(): Queue {
        return Queue(BL_TRAIN_01_MERCHANDISE_QUEUE, true)
    }

    @Bean("TrainMerchandiseExchange")
    fun exchange(): FanoutExchange {
        return FanoutExchange(BL_TRAIN_01_MERCHANDISE_EXCHANGE, true, false)
    }

    @Bean("TrainMerchandiseBinding")
    fun binding(
        @Qualifier("TrainMerchandiseQueue") queue: Queue?,
        @Qualifier("TrainMerchandiseExchange") exchange: FanoutExchange?
    ): Binding {
        return BindingBuilder.bind(queue)
            .to(exchange)
    }

    @Bean("TrainMerchandiseContainer")
    fun container(
        @Qualifier("TrainMerchandiseListener") listenerAdapter: MessageListenerAdapter?
    ): SimpleMessageListenerContainer? {
        return getSimpleMessageListenerContainer(listenerAdapter, vHost, BL_TRAIN_01_MERCHANDISE_QUEUE)
    }

    @Bean("TrainMerchandiseListener")
    fun listenerAdapter(trainMerchandiseReceiver: TrainMerchandiseReceiver?): MessageListenerAdapter {
        return MessageListenerAdapter(trainMerchandiseReceiver, "receiveMessage")
    }

    companion object {
        private const val BL_TRAIN_01_MERCHANDISE_EXCHANGE = "bl-train-01-merchandise-exchange"
        private const val BL_TRAIN_01_MERCHANDISE_QUEUE = "bl-train-01-merchandise-queue"
    }
}