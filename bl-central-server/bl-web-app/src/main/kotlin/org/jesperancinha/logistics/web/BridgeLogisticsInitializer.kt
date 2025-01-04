package org.jesperancinha.logistics.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.jesperancinha.logistics.jpa.dao.*
import org.jesperancinha.logistics.web.dto.CarriageFullDto
import org.jesperancinha.logistics.web.dto.ContainerFullDto
import org.jesperancinha.logistics.web.dto.FreightDto
import org.jesperancinha.logistics.web.dto.TrainDto
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.data.repository.findByIdOrNull
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*
import java.util.stream.Collectors
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Component
@Profile("default", "local", "demo")
class BridgeLogisticsInitializer(
    private val bridgeRepository: BridgeRepository,
    private val carriageRepository: CarriageRepository,
    private val containerRepository: ContainerRepository,
    private val freightRepository: FreightRepository,
    private val trainRepository: TrainRepository,
    private val openingTimeRepository: OpeningTimeRepository,
    private val companyRepository: CompanyRepository,
    private val jdbcTemplate: JdbcTemplate,
) : CommandLineRunner {
    private val objectMapper = ObjectMapper()

    @Throws(Exception::class)
    @Transactional
    override fun run(vararg args: String) {
        insertRows(
            "bridge", listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/bridges.json"),
                    Array<Bridge>::class.java
                )
            )
        )
        insertRows(
            "company",
            listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/companies.json"),
                    Array<Company>::class.java
                )
            )
        )
        insertRows(
            "carriage",
            listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/carriages.json"),
                    Array<Carriage>::class.java
                )
            )
        )
        insertRows(
            "container",
            listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/containers.json"),
                    Array<Container>::class.java
                )
            )
        )
        insertRows(
            "product",
            listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/products.json"),
                    Array<Product>::class.java
                )
            )
        )
        insertRows(
            "passengers",
            listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/passengers/passengers.json"),
                    Array<Passenger>::class.java
                )
            )
        )
        val freightDtos = listOf(
            *objectMapper.readValue(
                javaClass.getResourceAsStream("/freight.json"),
                Array<FreightDto>::class.java
            )
        )
        insertRows(
            "freight", freightDtos
        )
        freightDtos.map { freightDto: FreightDto ->
            Freight(
                id = freightDto.id,
                name = freightDto.name,
                type = freightDto.type,
                containers = freightDto.composition.mapNotNull { containerFullDto: ContainerFullDto ->
                    containerRepository.findById(containerFullDto.containerId)
                        .orElse(null)
                },
                supplier = companyRepository.findByIdOrNull(freightDto.supplierId),
                vendor =
                    companyRepository.findByIdOrNull(freightDto.vendorId)
            )
        }.forEach { freightRepository.save(it) }

        val trainDtos =
            listOf(*objectMapper.readValue(javaClass.getResourceAsStream("/train.json"), Array<TrainDto>::class.java))
        insertRows(
            "trains",
            trainDtos
        )
        trainDtos.map { trainDto ->
            Train(
                id = trainDto.id,
                name = trainDto.name,
                type = trainDto.type,
                carriages = trainDto.composition
                    .stream()
                    .map { carriageFullDto: CarriageFullDto ->
                        carriageRepository.findById(carriageFullDto.carriageId)
                            .orElse(null)
                    }
                    .filter { obj: Carriage? -> Objects.nonNull(obj) }
                    .collect(Collectors.toList()),
                supplier =
                    companyRepository.findByIdOrNull(trainDto.supplierId),
                vendor =
                    companyRepository.findByIdOrNull(trainDto.vendorId),
            )
        }.forEach { trainRepository.save(it) }

        jdbcTemplate.update("delete from bridge_opening_times")
        bridgeRepository.findAll()
            .forEachIndexed { i, bridge ->
                val now = Instant.now()
                val millisToAdd: Long = 10000
                val openingTimes = (0..200)
                    .map { integer: Int ->
                        BridgeOpeningTime(
                            id = (integer + 1 + (i * 200 + 1 * i)).toLong(),
                            bridge = bridge,
                            openingTime =
                                now.plusMillis(millisToAdd * integer * 2)
                                    .toEpochMilli(),
                            closingTime =
                                now.plusMillis(millisToAdd * (integer * 2 + 1))
                                    .toEpochMilli()
                        )
                    }
                insertRows("bridge_opening_times", openingTimes, "bridge_id" to bridge.id)
                openingTimeRepository.saveAll(openingTimes.map { it.copy(bridge = bridge) })
            }
    }

    fun camelCaseToUnderscore(input: String): String {
        return input.replace(Regex("([a-z])([A-Z])"), "$1_$2")
            .lowercase()
    }

    fun <T : Any> insertRows(tableName: String, dataList: List<T>, pair: Pair<String, Long?>? = null) {
        if (dataList.isEmpty()) {
            println("No data to insert.")
            return
        }
        val properties = dataList.first()::class.memberProperties
            .filterIsInstance<KProperty1<T, *>>()
            .filter {
                it.name != "bridgeOpeningTimes" && it.name != "composition" && it.name != "bridge"
            }
        val fieldNames =
            properties.map { camelCaseToUnderscore(it.name) } + (pair?.let { listOf(it.first) } ?: emptyList())
        val placeholders = fieldNames.joinToString(", ") { "?" }
        val sql = "INSERT INTO $tableName (${fieldNames.joinToString(", ")}) VALUES ($placeholders)"
        val batchArgs = dataList.map { obj ->
            val toTypedArray = properties.map { prop -> prop.get(obj) }.toTypedArray()
            pair?.second?.let { toTypedArray + it } ?: toTypedArray
        }
        val rowsAffected = jdbcTemplate.batchUpdate(sql, batchArgs)
        println("Rows inserted: ${rowsAffected.sum()}")
    }

    companion object {
        val logger = LoggerFactory.getLogger(BridgeLogisticsInitializer::class.java)
    }
}