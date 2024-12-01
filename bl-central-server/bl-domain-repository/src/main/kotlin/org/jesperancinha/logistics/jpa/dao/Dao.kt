package org.jesperancinha.logistics.jpa.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.math.BigDecimal
import java.util.UUID

interface BridgeRepository : CrudRepository<Bridge, UUID> {
    fun findBridgeBySquareBoundary(
        @Param("latWest") latWest: BigDecimal,
        @Param("latEast") latEast: BigDecimal,
        @Param("lonNorth") lonNorth: BigDecimal,
        @Param("lonSouth") lonSouth: BigDecimal
    ): List<Bridge?>?
}

interface CarriageRepository : CrudRepository<Carriage, UUID>
interface CompanyRepository : CrudRepository<Company, UUID>
interface ContainerRepository : CrudRepository<Container, UUID>
interface FreightRepository : CrudRepository<Freight, UUID>
interface LogRepository : CrudRepository<BridgeLog, UUID>
interface MerchandiseLogRepository : CrudRepository<MerchandiseLog, UUID>
interface MerchandiseRepository : CrudRepository<Merchandise, UUID>
interface OpeningConflictRepository : CrudRepository<BridgeOpeningConflict, UUID>
interface OpeningTimeRepository : CrudRepository<BridgeOpeningTime, UUID> {
    fun findBridgeBySquareBoundaryUnderRadius(
        @Param("latWest") latWest: BigDecimal,
        @Param("latEast") latEast: BigDecimal,
        @Param("lonNorth") lonNorth: BigDecimal,
        @Param("lonSouth") lonSouth: BigDecimal,
        @Param("milliseconds") milliseconds: Long?
    ): List<BridgeOpeningTime>
}

interface PassengerRepository : CrudRepository<Passenger, UUID>
interface ProductCargoRepository : CrudRepository<ProductCargo, UUID>
interface ProductRepository : CrudRepository<Product, UUID>
interface TrainRepository : CrudRepository<Train, UUID>
interface TrainsLogRepository : CrudRepository<TrainLog, UUID>
interface TransportPackageRepository : CrudRepository<TransportPackage, UUID>