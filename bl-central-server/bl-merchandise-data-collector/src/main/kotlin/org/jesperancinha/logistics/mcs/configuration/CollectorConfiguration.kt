package org.jesperancinha.logistics.mcs.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

abstract class CollectorConfiguration {
    @Value("\${spring.rabbitmq.username}")
    private val username: String? = null

    @Value("\${spring.rabbitmq.password}")
    private val password: String? = null

    @Value("\${spring.rabbitmq.host}")
    private val host: String? = null

    @Bean
    open fun objectMapper(): ObjectMapper = ObjectMapper()

    protected fun getSimpleMessageListenerContainer(
        listenerAdapter: MessageListenerAdapter?,
        vHost: String?,
        queueName: String?
    ): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        val connectionFactory = CachingConnectionFactory(host)
        connectionFactory.username = username
        connectionFactory.setPassword(password)
        connectionFactory.virtualHost = vHost
        container.connectionFactory = connectionFactory
        container.setQueueNames(queueName)
        container.setMessageListener(listenerAdapter)
        return container
    }
}