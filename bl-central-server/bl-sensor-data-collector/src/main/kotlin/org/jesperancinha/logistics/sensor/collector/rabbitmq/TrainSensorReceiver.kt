package org.jesperancinha.logistics.sensor.collector.rabbitmq

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.jesperancinha.logistics.jpa.dao.TrainRepository
import org.jesperancinha.logistics.jpa.dao.TrainsLogRepository
import org.jesperancinha.logistics.sensor.collector.converter.TrainConverter
import org.jesperancinha.logistics.sensor.collector.data.TrainLogDto
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.CountDownLatch

@Component
@ConditionalOnProperty(name = ["bridge.logistics.train.sensor.active"], matchIfMissing = true)
class TrainSensorReceiver(
    private val objectMapper: ObjectMapper,
    private val trainsLogRepository: TrainsLogRepository,
    private val trainRepository: TrainRepository
) {
    val latch = CountDownLatch(1)

    @Throws(JsonProcessingException::class)
    fun receiveMessage(message: ByteArray) {
        val messageString = String(message, Charset.defaultCharset())
        println("Received <$messageString>")
        val trainLogDto = objectMapper.readValue(messageString, TrainLogDto::class.java)
        if (Objects.nonNull(trainLogDto.id)) {
            trainsLogRepository.save(
                TrainConverter.toModel(
                    trainLogDto, trainRepository.findById(trainLogDto.id)
                        .orElse(null)
                )
            )
        }
        println("Received <$messageString>")
    }
}