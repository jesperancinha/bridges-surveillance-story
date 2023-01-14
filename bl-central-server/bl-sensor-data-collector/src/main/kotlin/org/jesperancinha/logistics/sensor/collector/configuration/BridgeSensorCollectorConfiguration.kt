package org.jesperancinha.logistics.sensor.collector.configuration

import org.jesperancinha.logistics.sensor.collector.rabbitmq.BridgeSensorReceiver
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
@ConditionalOnProperty(name = ["bridge.logistics.bridge.sensor.active"], matchIfMissing = true)
class BridgeSensorCollectorConfiguration : CollectorConfiguration() {
    @Value("\${bridge.logistics.bridge.sensor.vhost}")
    private val vHost: String? = null
    @Bean(name = ["BridgeQueue"])
    fun queue(): Queue {
        return Queue(BL_BRIDGE_01_SENSOR_QUEUE, true)
    }

    @Bean(name = ["BridgeExchange"])
    fun exchange(): FanoutExchange {
        return FanoutExchange(BL_BRIDGE_01_SENSOR_EXCHANGE, true, false)
    }

    @Bean(name = ["BridgeBinding"])
    fun binding(
        @Qualifier("BridgeQueue") queue: Queue?,
        @Qualifier("BridgeExchange") exchange: FanoutExchange?
    ): Binding {
        return BindingBuilder.bind(queue)
            .to(exchange)
    }

    @Bean(name = ["BridgeContainer"])
    fun container(
        @Qualifier("BridgeListener") listenerAdapter: MessageListenerAdapter?
    ): SimpleMessageListenerContainer? {
        return getSimpleMessageListenerContainer(listenerAdapter, vHost, BL_BRIDGE_01_SENSOR_QUEUE)
    }

    @Bean(name = ["BridgeListener"])
    fun listenerAdapter(bridgeSensorReceiver: BridgeSensorReceiver?): MessageListenerAdapter {
        return MessageListenerAdapter(bridgeSensorReceiver, "receiveMessage")
    }

    companion object {
        private const val BL_BRIDGE_01_SENSOR_EXCHANGE = "bl-bridge-01-sensor-exchange"
        private const val BL_BRIDGE_01_SENSOR_QUEUE = "bl-bridge-01-sensor-queue"
    }
}