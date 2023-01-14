package org.jesperancinha.logistics.sensor.collector.rabbitmq

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.jesperancinha.logistics.jpa.dao.BridgeRepository
import org.jesperancinha.logistics.jpa.dao.LogRepository
import org.jesperancinha.logistics.sensor.collector.converter.BridgeConverter
import org.jesperancinha.logistics.sensor.collector.data.BridgeLogDto
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.CountDownLatch

@Component
@ConditionalOnProperty(name = ["bridge.logistics.bridge.sensor.active"], matchIfMissing = true)
class BridgeSensorReceiver(
    private val objectMapper: ObjectMapper,
    private val logRepository: LogRepository,
    private val bridgeRepository: BridgeRepository
) {
    val latch = CountDownLatch(1)

    @Throws(JsonProcessingException::class)
    fun receiveMessage(message: ByteArray) {
        val messageString = String(message, Charset.defaultCharset())
        val bridgeLogDto = objectMapper.readValue(messageString, BridgeLogDto::class.java)
        if (Objects.nonNull(bridgeLogDto.id)) {
            val bridge = bridgeRepository.findById(bridgeLogDto.id)
                .orElse(null)
            logRepository.save(BridgeConverter.toModel(bridgeLogDto, bridge))
            println("Received <$messageString>")
        } else {
            println("Received empty id message <$messageString>")
        }
    }
}