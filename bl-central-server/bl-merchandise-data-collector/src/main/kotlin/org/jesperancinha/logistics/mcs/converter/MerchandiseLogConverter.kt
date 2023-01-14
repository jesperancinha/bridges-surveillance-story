package org.jesperancinha.logistics.mcs.converter

import org.jesperancinha.logistics.jpa.dao.Merchandise
import org.jesperancinha.logistics.jpa.dao.MerchandiseLog

object MerchandiseLogConverter {
    fun toMerchandise(merchandiseLog: MerchandiseLog): Merchandise {
        return Merchandise(
            productCargo = merchandiseLog.productCargo,
            supplier = merchandiseLog.supplier,
            vendor = merchandiseLog.vendor,
            timestamp = merchandiseLog.timestamp,
            transportPackage = merchandiseLog.transportPackage,
            lat = merchandiseLog.lat,
            lon = merchandiseLog.lon
        )
    }
}