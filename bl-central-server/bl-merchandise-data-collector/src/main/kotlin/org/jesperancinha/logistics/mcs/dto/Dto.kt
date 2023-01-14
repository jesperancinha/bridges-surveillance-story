package org.jesperancinha.logistics.mcs.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.jesperancinha.logistics.jpa.dao.Status
import org.jesperancinha.logistics.jpa.dao.TrainType
import java.math.BigDecimal

data class CarrierDto(
    @JsonProperty("carriageId")
    val carriageId: Long,
    @JsonProperty("containerId")
    val containerId: Long,
    @JsonProperty("packageId")
    val packageId: Long,
    @JsonProperty("weight")
    val weight: Long,
    @JsonProperty("products")
    val products: List<ProductInTransitDto>
)

data class ContainerDto(
    @JsonProperty("containerId")
    val containerId: Long,
    val packageId: Long,
    val products: List<ProductInTransitDto>
)

class ProductInTransitDto(
    @JsonProperty("productId")
    val productId: Long,
    @JsonProperty("quantity")
    val quantity: Long
)

data class TrainMerchandiseDto(
    @JsonProperty("id")
    val id: Long?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("supplierId")
    val supplierId: Long?,
    @JsonProperty("vendorId")
    val vendorId: Long?,
    @JsonProperty("type")
    val type: TrainType?,
    @JsonProperty("status")
    val status: Status?,
    @JsonProperty("composition")
    val composition: List<CarrierDto>?,
    @JsonProperty("lat")
    val lat: BigDecimal?,
    @JsonProperty("lon")
    val lon: BigDecimal?
)