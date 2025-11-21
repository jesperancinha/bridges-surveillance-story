package org.jesperancinha.logistics.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.hibernate.Session
import org.jesperancinha.logistics.jpa.dao.*
import org.jesperancinha.logistics.web.dto.CarriageFullDto
import org.jesperancinha.logistics.web.dto.ContainerFullDto
import org.jesperancinha.logistics.web.dto.FreightDto
import org.jesperancinha.logistics.web.dto.TrainDto
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors

@Component
@Profile("default", "local", "demo")
@Transactional
class BridgeLogisticsInitializer(
    private val bridgeRepository: BridgeRepository,
    private val carriageRepository: CarriageRepository,
    private val containerRepository: ContainerRepository,
    private val freightRepository: FreightRepository,
    private val productRepository: ProductRepository,
    private val trainRepository: TrainRepository,
    private val openingTimeRepository: OpeningTimeRepository,
    private val companyRepository: CompanyRepository,
    private val passengerRepository: PassengerRepository
) : CommandLineRunner {
    private val objectMapper = ObjectMapper()

    @Transactional
    override fun run(vararg args: String?) {
        bridgeRepository.deleteAll()
        companyRepository.deleteAll()
        carriageRepository.deleteAll()
        containerRepository.deleteAll()
        freightRepository.deleteAll()
        trainRepository.deleteAll()
        openingTimeRepository.deleteAll()
        bridgeRepository.saveAll(
            listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/bridges.json"),
                    Array<Bridge>::class.java
                )
            ).map { bridge: Bridge -> bridge.copy(id = null) }
        )
        companyRepository.saveAll(
            listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/companies.json"),
                    Array<Company>::class.java
                )
            )
        )
        carriageRepository.saveAll(
            listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/carriages.json"),
                    Array<Carriage>::class.java
                )
            ).map { carriage: Carriage -> carriage.copy(id = null) }
        )
        containerRepository.saveAll(
            listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/containers.json"),
                    Array<Container>::class.java
                )
            ).map { container: Container -> container.copy(id = null) }
        )
        productRepository.saveAll(
            listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/products.json"),
                    Array<Product>::class.java
                )
            ).map { product: Product -> product.copy(id = null) }
        )
        passengerRepository.saveAll(
            listOf(
                *objectMapper.readValue(
                    javaClass.getResourceAsStream("/passengers/passengers.json"),
                    Array<Passenger>::class.java
                )
            ).map { passenger: Passenger -> passenger.copy(id = null) }
        )
        objectMapper.readValue(
            javaClass.getResourceAsStream("/freight.json"),
            Array<FreightDto>::class.java
        ).map { freightDto: FreightDto ->
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
        }
            .map { freight: Freight -> freight.copy(id = null) }
            .forEach(freightRepository::save)

        objectMapper.readValue(javaClass.getResourceAsStream("/train.json"), Array<TrainDto>::class.java)
            .map { trainDto ->
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
            }
            .map { train: Train -> train.copy(id = null) }
            .forEach(trainRepository::save)

        bridgeRepository.findAll()
            .forEach { bridge ->
                val now = Instant.now()
                val millisToAdd: Long = 10000
                (0..200)
                    .map { integer: Int ->
                        BridgeOpeningTime(
                            bridge = bridge,
                            openingTime =
                                now.plusMillis(millisToAdd * integer * 2)
                                    .toEpochMilli(),
                            closingTime =
                                now.plusMillis(millisToAdd * (integer * 2 + 1))
                                    .toEpochMilli()
                        )
                    }
                    .forEach(openingTimeRepository::save)
            }
    }
}