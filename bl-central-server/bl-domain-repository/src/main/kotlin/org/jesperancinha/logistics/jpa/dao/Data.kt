package org.jesperancinha.logistics.jpa.dao

import org.hibernate.Hibernate
import java.math.BigDecimal
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "bridge")
@NamedQuery(
    name = "Bridge.findBridgeBySquareBoundary", query = "select b from Bridge b"
            + " where b.lat>=:latWest and b.lat<=:latEast and b.lon<=:lonNorth and b.lon>=:lonSouth"
)
data class Bridge(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val name: String? = null,
    val address: String? = null,
    val city: String? = null,
    val postCode: String? = null,
    val countryCode: String? = null,
    val unitLength: String? = null,
    val length: Long? = null,

    @Column(precision = 10, scale = 6)
    val lat: BigDecimal? = null,

    @Column(precision = 10, scale = 6)
    val lon: BigDecimal? = null,
    val radius: Long? = null,
    val type: String? = null,


    ) {
    @OneToMany(mappedBy = "bridge")
    var bridgeOpeningTimes: List<BridgeOpeningTime>? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val bridge = other as Bridge
        return id == bridge.id
    }
}

@Entity
@Table(name = "bridge_logs")
data class BridgeLog(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "bridgeId", nullable = true, insertable = true, updatable = true)
    val bridge: Bridge? = null,
    val timestamp: Long? = null,
    val checkInOut: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val bridgeLog = other as BridgeLog
        return id == bridgeLog.id
    }
}

/**
 * This object is made when a conflict is found.
 * It will accumulate all conflicts found in reference to the first element in the list
 */
@Entity
@Table(name = "bridge_opening_conflicts")
data class BridgeOpeningConflict(
    @Id
    val message: String? = null,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(joinColumns = [JoinColumn(name = "bridge_id", nullable = true, updatable = true, insertable = true)])
    val relatedOpeningTime: List<BridgeOpeningTime>? = null
)

/**
 * This object serves to determine a time slot to open and close a bridge
 */
@Entity
@Table(name = "bridge_opening_times")
@NamedQuery(
    name = "BridgeOpeningTime.findBridgeBySquareBoundaryUnderRadius", query = "select bot from BridgeOpeningTime bot"
            + " where bot.bridge.lat>=:latWest and bot.bridge.lat<=:latEast and bot.bridge.lon<=:lonNorth and bot.bridge.lon>=:lonSouth"
            + " and bot.openingTime <= :milliseconds and bot.closingTime > :milliseconds "
)
data class BridgeOpeningTime(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID? = null,

    @Column(name = "opening_time")
    val openingTime: Long? = null,

    @Column(name = "closing_time")
    val closingTime: Long? = null,

    @ManyToOne(optional = false, cascade = [CascadeType.ALL])
    @JoinColumn(name = "bridge_id", nullable = true, updatable = true, referencedColumnName = "id")
    val bridge: Bridge? = null
)

@Entity
@Table(name = "carriage")
data class Carriage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val type: String? = null,
    val brand: String? = null,
    val capacity: Long? = null,
    val passengers: Long? = null,
    val axleLoad: Long? = null,
    val tare: Long? = null,
    val volume: Long? = null,
    val unitWeight: String? = null,
    val unitVolume: String? = null,
    val toilet: Boolean? = null,
)

@Entity

@Table(name = "company")
data class Company(
    @Id
    val id: UUID? = null,
    val name: String? = null,
    val address: String? = null,
    val city: String? = null,
    val postCode: String? = null

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val company = other as Company
        return id == company.id
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (city?.hashCode() ?: 0)
        result = 31 * result + (postCode?.hashCode() ?: 0)
        return result
    }
}

@Entity
@Table(name = "container")
data class Container(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val type: String? = null,
    val brand: String? = null,
    val model: String? = null,
    val capacity: Long? = null,
    val axleLoad: Long? = null,
    val tare: Long? = null,
    val volume: Long? = null,
    val unitWeight: String? = null,
    val unitVolume: String? = null
)

@Entity
@Table(name = "freight")
data class Freight(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID? = null,
    val name: String? = null,
    val type: String? = null,

    @ManyToMany
    @JoinTable(
        name = "freight_container",
        joinColumns = [JoinColumn(name = "freight_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "container_id", referencedColumnName = "id")]
    )
    val containers: List<Container>? = null,

    @ManyToOne
    val supplier: Company? = null,

    @ManyToOne
    val vendor: Company? = null
)

@Entity
@Table(name = "merchandise")
data class Merchandise(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val timestamp: Long? = null,

    @ManyToOne
    val supplier: Company? = null,

    @ManyToOne
    val vendor: Company? = null,

    @ManyToOne
    val transportPackage: TransportPackage? = null,

    @ManyToOne
    val productCargo: ProductCargo? = null,

    @Column(precision = 10, scale = 6)
    val lat: BigDecimal? = null,

    @Column(precision = 10, scale = 6)
    val lon: BigDecimal? = null
)

@Entity
@Table(name = "merchandise_log")
data class MerchandiseLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    val timestamp: Long? = null,

    /**
     * Status can be LOADED, INTRANSIT, DELIVERED
     */
    val status: Status? = null,

    @ManyToOne
    val supplier: Company? = null,

    @ManyToOne
    val vendor: Company? = null,

    @ManyToOne
    val transportPackage: TransportPackage? = null,

    @ManyToOne
    val productCargo: ProductCargo? = null,

    @Column(precision = 10, scale = 6)
    val lat: BigDecimal? = null,

    @Column(precision = 10, scale = 6)
    val lon: BigDecimal? = null
)

@Entity
@Table(name = "passengers")
data class Passenger(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val gender: String? = null
)

@Entity
@Table(name = "product")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID? = null,
    val name: String? = null,
    val packaging: String? = null,
    val brand: String? = null,
    val packageSize: Long? = null,
    val weightPerUnit: Long? = null,
    val unitWeight: String? = null,
    val barcode: String? = null,
    val correctionFactor: Long? = null,
    val width: Long? = null,
    val height: Long? = null,
    val depth: Long? = null,
    val unitDimension: String? = null
)

@Entity
@Table(name = "product_cargo")
data class ProductCargo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID? = null,

    @ManyToOne
    val product: Product? = null,
    val quantity: Long? = null
)

@Entity
@Table(name = "trains")
data class Train(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID? = null,
    val name: String? = null,
    val type: String? = null,

    @ManyToMany
    @JoinTable(
        name = "train_container",
        joinColumns = [JoinColumn(name = "train_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "carriage_id", referencedColumnName = "id")]
    )
    val carriages: List<Carriage>? = null,

    @ManyToOne
    val supplier: Company? = null,

    @ManyToOne
    val vendor: Company? = null
)

@Entity
@Table(name = "trains_log")
data class TrainLog(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "trainId", nullable = false, updatable = true, insertable = true)
    val train: Train? = null,
    val lat: BigDecimal? = null,
    val lon: BigDecimal? = null,
    val timestamp: Long? = null,
    val checkInOut: String? = null,
    val weight: Long? = null,
    val carriageId: UUID? = null
)

@Entity
@Table(name = "transport_package")
data class TransportPackage(
    @Id
    val id: UUID? = null,

    @ManyToOne
    val supplier: Company? = null,

    @ManyToOne
    val vendor: Company? = null,

    @OneToMany
    val productCargos: MutableList<ProductCargo>? = null,

    @ManyToOne
    val carriage: Carriage? = null,

    @ManyToOne
    val container: Container? = null,
    val weight: Long? = null
)

enum class Status {
    LOADED, INTRANSIT, DELIVERED
}

enum class TrainType {
    INTERCITY, SPRINTER, CARGO
}

enum class Transport {
    TRAIN, VEHICLE
}