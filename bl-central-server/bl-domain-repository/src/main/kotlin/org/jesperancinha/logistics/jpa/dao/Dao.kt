package org.jesperancinha.logistics.jpa.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.math.BigDecimal

interface BridgeRepository : CrudRepository<Bridge, Long> {
    fun findBridgeBySquareBoundary(
        @Param("latWest") latWest: BigDecimal,
        @Param("latEast") latEast: BigDecimal,
        @Param("lonNorth") lonNorth: BigDecimal,
        @Param("lonSouth") lonSouth: BigDecimal
    ): List<Bridge?>?
}

interface CarriageRepository : CrudRepository<Carriage, Long>
interface CompanyRepository : CrudRepository<Company, Long>
interface ContainerRepository : CrudRepository<Container, Long>
interface FreightRepository : CrudRepository<Freight, Long>
interface LogRepository : CrudRepository<BridgeLog, Long>
interface MerchandiseLogRepository : CrudRepository<MerchandiseLog, Long>
interface MerchandiseRepository : CrudRepository<Merchandise, Long>
interface OpeningConflictRepository : CrudRepository<BridgeOpeningConflict, Long>
interface OpeningTimeRepository : CrudRepository<BridgeOpeningTime, Long> {
    fun findBridgeBySquareBoundaryUnderRadius(
        @Param("latWest") latWest: BigDecimal,
        @Param("latEast") latEast: BigDecimal,
        @Param("lonNorth") lonNorth: BigDecimal,
        @Param("lonSouth") lonSouth: BigDecimal,
        @Param("milliseconds") milliseconds: Long?
    ): List<BridgeOpeningTime>
}

interface PassengerRepository : CrudRepository<Passenger, Long>
interface ProductCargoRepository : CrudRepository<ProductCargo, Long>
interface ProductRepository : CrudRepository<Product, Long>
interface TrainRepository : CrudRepository<Train, Long>
interface TrainsLogRepository : CrudRepository<TrainLog, Long>
interface TransportPackageRepository : CrudRepository<TransportPackage, Long>