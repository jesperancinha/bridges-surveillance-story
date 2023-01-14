package org.jesperancinha.logistics.mcs.rabbitmq

import com.fasterxml.jackson.databind.ObjectMapper
import lombok.extern.slf4j.Slf4j
import org.jesperancinha.logistics.jpa.dao.*
import org.jesperancinha.logistics.mcs.converter.MerchandiseLogConverter
import org.jesperancinha.logistics.mcs.dto.CarrierDto
import org.jesperancinha.logistics.mcs.dto.ProductInTransitDto
import org.jesperancinha.logistics.mcs.dto.TrainMerchandiseDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.nio.charset.Charset
import java.time.Instant
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.stream.Stream

@Slf4j
@Component
class TrainMerchandiseReceiver(
    private val objectMapper: ObjectMapper,
    private val merchandiseLogRepository: MerchandiseLogRepository,
    private val merchandiseRepository: MerchandiseRepository,
    private val productRepository: ProductRepository,
    private val transportPackageRepository: TransportPackageRepository,
    private val companyRepository: CompanyRepository,
    private val productCargoRepository: ProductCargoRepository,
    private val carriageRepository: CarriageRepository
) {
    val latch = CountDownLatch(1)
    fun receiveMessage(message: ByteArray) {
        val messageString = String(message, Charset.defaultCharset())
        println("Received <$messageString>")
        try {
            val trainMerchandiseDtos = objectMapper.readValue(messageString, Array<TrainMerchandiseDto>::class.java)
            Stream.of(*trainMerchandiseDtos)
                .forEach { trainMerchandiseDto: TrainMerchandiseDto ->
                    val supplier = if (Objects.nonNull(trainMerchandiseDto.supplierId)) {
                        companyRepository.findById(trainMerchandiseDto.supplierId)
                            .orElse(null)
                    } else {
                        null
                    }
                    val vendor = if (Objects.nonNull(trainMerchandiseDto.vendorId)) {
                        companyRepository.findById(trainMerchandiseDto.vendorId)
                            .orElse(null)
                    } else {
                        null
                    }
                    trainMerchandiseDto.composition
                        ?.forEach { carrierDto: CarrierDto ->
                            val packageId = carrierDto.packageId
                            val transportPackage: TransportPackage = TransportPackage(
                                id = packageId,
                                supplier = supplier,
                                vendor = vendor,
                                weight = carrierDto.weight,
                                carriage =
                                carriageRepository.findByIdOrNull(carrierDto.carriageId),
                                productCargos = ArrayList()
                            )
                            val transportPackage1 = transportPackageRepository.save(transportPackage)
                            if (Objects.nonNull(carrierDto.products)) {
                                carrierDto.products
                                    .parallelStream()
                                    .forEach { productInTransitDto: ProductInTransitDto ->
                                        val product = productRepository.findById(productInTransitDto.productId)
                                            .orElse(null)
                                        val productCargo: ProductCargo = ProductCargo(
                                            product = product,
                                            quantity = productInTransitDto.quantity
                                        )
                                        val productCargoDb = productCargoRepository.save(productCargo)
                                        val merchandiseLog: MerchandiseLog = MerchandiseLog(
                                            supplier = supplier,
                                            vendor = vendor,
                                            timestamp = Instant.now().toEpochMilli(),
                                            transportPackage = transportPackage1,
                                            productCargo = productCargoDb,
                                            status = trainMerchandiseDto.status,
                                            lat = trainMerchandiseDto.lat,
                                            lon = trainMerchandiseDto.lon
                                        )
                                        transportPackage1.productCargos?.add(productCargoDb)
                                        merchandiseLogRepository.save(merchandiseLog)
                                        if (merchandiseLog.status === Status.DELIVERED) {
                                            merchandiseRepository.save(
                                                MerchandiseLogConverter.toMerchandise(
                                                    merchandiseLog
                                                )
                                            )
                                        }
                                    }
                            }
                            transportPackageRepository.save(transportPackage1)
                        }
                    latch.countDown()
                }
        } catch (e: Exception) {
            logger.error("Error receiving message!", e)
        }
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(TrainMerchandiseReceiver::class.java)
    }
}