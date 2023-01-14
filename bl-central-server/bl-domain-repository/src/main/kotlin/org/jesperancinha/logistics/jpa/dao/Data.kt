package org.jesperancinha.logistics.jpa.dao

import org.hibernate.Hibernate
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "bridge")
@NamedQuery(
    name = "Bridge.findBridgeBySquareBoundary", query = "select b from Bridge b"
            + " where b.lat>=:latWest and b.lat<=:latEast and b.lon<=:lonNorth and b.lon>=:lonSouth"
)
data class Bridge(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = null,
    val name: String? = null,
    val address: String? = null,
    private val city: String? = null,
    val postCode: String? = null,
    private val countryCode: String? = null,
    private val unitLength: String? = null,
    private val length: Long? = null,

    @Column(precision = 10, scale = 6)
    private val lat: BigDecimal? = null,

    @Column(precision = 10, scale = 6)
    private val lon: BigDecimal? = null,
    private val radius: Long? = null,
    private val type: String? = null,


    ) {
    @OneToMany(mappedBy = "bridge")
    private var bridgeOpeningTimes: List<BridgeOpeningTime>? = null

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "bridgeId", nullable = false)
    private val bridge: Bridge? = null,
    private val timestamp: Long? = null,
    private val checkInOut: String? = null
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
    private val message: String? = null,

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "bridge_id", nullable = false, updatable = false)
    private val relatedOpeningTime: List<BridgeOpeningTime>? = null
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
    private val id: Long? = null,

    @Column(name = "opening_time")
    private val openingTime: Long? = null,

    @Column(name = "closing_time")
    private val closingTime: Long? = null,

    @ManyToOne(optional = false, cascade = [CascadeType.ALL])
    @JoinColumn(name = "bridge_id", nullable = false, updatable = false, referencedColumnName = "id")
    private val bridge: Bridge? = null
)

@Entity
@Table(name = "carriage")
data class Carriage (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    private val type: String? = null,
    private val brand: String? = null,
    private val capacity: Long? = null,
    private val passengers: Long? = null,
    private val axleLoad: Long? = null,
    private val tare: Long? = null,
    private val volume: Long? = null,
    private val unitWeight: String? = null,
    private val unitVolume: String? = null,
    private val toilet: Boolean? = null,
)

@Entity

@Table(name = "company")
data class Company (
    @Id
    private val id: Long? = null,
    private val name: String? = null,
    private val address: String? = null,
    private val city: String? = null,
    private val postCode: String? = null

){
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
    private val type: String? = null,
    private val brand: String? = null,
    private val model: String? = null,
    private val capacity: Long? = null,
    private val axleLoad: Long? = null,
    private val tare: Long? = null,
    private val volume: Long? = null,
    private val unitWeight: String? = null,
    private val unitVolume: String? = null
)

@Entity
@Table(name = "freight")
data class Freight(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,
    private val name: String? = null,
    private val type: String? = null,

    @ManyToMany
    @JoinTable(
        name = "freight_container",
        joinColumns = [JoinColumn(name = "freight_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "container_id", referencedColumnName = "id")]
    )
    private val containers: List<Container>? = null,

    @ManyToOne
    private val supplier: Company? = null,

    @ManyToOne
    private val vendor: Company? = null
)

@Entity
@Table(name = "merchandise")
data class Merchandise(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    private val timestamp: Long? = null,

    @ManyToOne
    private val supplier: Company? = null,

    @ManyToOne
    private val vendor: Company? = null,

    @ManyToOne
    private val transportPackage: TransportPackage? = null,

    @ManyToOne
    private val productCargo: ProductCargo? = null,

    @Column(precision = 10, scale = 6)
    private val lat: BigDecimal? = null,

    @Column(precision = 10, scale = 6)
    private val lon: BigDecimal? = null
)

@Entity
@Table(name = "merchandise_log")
data class MerchandiseLog (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    private val timestamp: Long? = null,

    /**
     * Status can be LOADED, INTRANSIT, DELIVERED
     */
    private val status: Status? = null,

    @ManyToOne
    private val supplier: Company? = null,

    @ManyToOne
    private val vendor: Company? = null,

    @ManyToOne
    private val transportPackage: TransportPackage? = null,

    @ManyToOne
    private val productCargo: ProductCargo? = null,

    @Column(precision = 10, scale = 6)
    private val lat: BigDecimal? = null,

    @Column(precision = 10, scale = 6)
    private val lon: BigDecimal? = null
)

@Entity
@Table(name = "passengers")
data class Passenger(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null,
    private val firstName: String? = null,
    private val lastName: String? = null,
    private val gender: String? = null
)

@Entity
@Table(name = "product")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,
    private val name: String? = null,
    private val packaging: String? = null,
    private val brand: String? = null,
    private val packageSize: Long? = null,
    private val weightPerUnit: Long? = null,
    private val unitWeight: String? = null,
    private val barcode: String? = null,
    private val correctionFactor: Long? = null,
    private val width: Long? = null,
    private val height: Long? = null,
    private val depth: Long? = null,
    private val unitDimension: String? = null
)

@Entity
@Table(name = "product_cargo")
data class ProductCargo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,

    @ManyToOne
    private val product: Product? = null,
    private val quantity: Long? = null
)

@Entity
@Table(name = "trains")
data class Train(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null,
    private val name: String? = null,
    private val type: String? = null,

    @ManyToMany
    @JoinTable(
        name = "train_container",
        joinColumns = [JoinColumn(name = "train_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "carriage_id", referencedColumnName = "id")]
    )
    private val carriages: List<Carriage>? = null,

    @ManyToOne
    private val supplier: Company? = null,

    @ManyToOne
    private val vendor: Company? = null
)

@Entity
@Table(name = "trains_log")
data class TrainLog (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "trainId", nullable = false, updatable = false)
    private val train: Train? = null,
    private val lat: BigDecimal? = null,
    private val lon: BigDecimal? = null,
    private val timestamp: Long? = null,
    private val checkInOut: String? = null,
    private val weight: Long? = null,
    private val carriageId: Long? = null
)

@Entity
@Table(name = "transport_package")
data class TransportPackage(
    @Id
    private val id: Long? = null,

    @ManyToOne
    private val supplier: Company? = null,

    @ManyToOne
    private val vendor: Company? = null,

    @OneToMany
    private val productCargos: List<ProductCargo>? = null,

    @ManyToOne
    private val carriage: Carriage? = null,

    @ManyToOne
    private val container: Container? = null,
    private val weight: Long? = null
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