package org.jesperancinha.logistics.web.dto

import java.time.LocalDateTime

data class BridgeDto(
    val name: String,
    val address: String? = null,
    val city: String? = null,
    val postCode: String? = null,
    val country: String? = null
)

/**
 * This object is made when a conflict is found.
 * It will accumulate all conflicts found in reference to the first element in the list
 */
data class BridgeOpeningConflictDto(
    val message: String? = null,
    val relatedOpeningTimes: MutableList<BridgeOpeningTimeDto>
)

/**
 * This object serves to determine a time slot to open and close a bridge
 */
data class BridgeOpeningTimeDto(
    val bridge: BridgeDto,
    val openingTime: LocalDateTime,
    val closingTime: LocalDateTime
)

data class CarriageFullDto(
    val carriageId: Long,
    val packageId: Long,
    val people: Long,
    val products: List<ProductDto>
)

data class ContainerFullDto(
    val containerId: Long,
    val packageId: Long,
    val products: List<ProductDto>
)

data class FreightDto(
    val id: Long,
    val name: String,
    val type: String,
    val supplierId: Long,
    val vendorId: Long,
    val composition: List<ContainerFullDto>
)

@JvmRecord
data class ProductDto(
    val productId: Long,
    val quantity: Long
)

data class TrainDto(
    val id: Long,
    val name: String,
    val type: String,
    val supplierId: Long,
    val vendorId: Long,
    val composition: List<CarriageFullDto>
)