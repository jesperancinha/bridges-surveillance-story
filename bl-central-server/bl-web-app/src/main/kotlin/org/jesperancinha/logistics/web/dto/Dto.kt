package org.jesperancinha.logistics.web.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class BridgeDto(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("address")
    val address: String? = null,
    @JsonProperty("city")
    val city: String? = null,
    @JsonProperty("postCode")
    val postCode: String? = null,
    @JsonProperty("country")
    val country: String? = null
)

/**
 * This object is made when a conflict is found.
 * It will accumulate all conflicts found in reference to the first element in the list
 */
data class BridgeOpeningConflictDto(
    @JsonProperty("message")
    val message: String? = null,
    @JsonProperty("relatedOpeningTimes")
    val relatedOpeningTimes: MutableList<BridgeOpeningTimeDto>
)

/**
 * This object serves to determine a time slot to open and close a bridge
 */
data class BridgeOpeningTimeDto(
    @JsonProperty("bridge")
    val bridge: BridgeDto,
    @JsonProperty("openingTime")
    val openingTime: LocalDateTime,
    @JsonProperty("closingTime")
    val closingTime: LocalDateTime
)

data class CarriageFullDto(
    @JsonProperty("carriageId")
    val carriageId: Long,
    @JsonProperty("packageId")
    val packageId: Long,
    @JsonProperty("people")
    val people: Long,
    @JsonProperty("products")
    val products: List<ProductDto>?
)

data class ContainerFullDto(
    @JsonProperty("containerId")
    val containerId: Long,
    @JsonProperty("packageId")
    val packageId: Long,
    @JsonProperty("products")
    val products: List<ProductDto>
)

data class FreightDto(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("type")
    val type: String?,
    @JsonProperty("supplierId")
    val supplierId: Long,
    @JsonProperty("vendorId")
    val vendorId: Long,
    @JsonProperty("composition")
    val composition: List<ContainerFullDto>
)

data class ProductDto(
    @JsonProperty("productId")
    val productId: Long,
    @JsonProperty("quantity")
    val quantity: Long
)

data class TrainDto(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("type")
    val type: String,
    @JsonProperty("supplierId")
    val supplierId: Long,
    @JsonProperty("vendorId")
    val vendorId: Long,
    @JsonProperty("composition")
    val composition: List<CarriageFullDto>
)