package org.jesperancinha.logistics.mcs.rabbitmq

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldNotBeNull
import org.jesperancinha.logistics.mcs.dto.TrainMerchandiseDto
import org.junit.jupiter.api.Test

/**
 * Created by jofisaes on 16/09/2021
 */
class TrainMerchandiseReceiverUnitTest {
    /**
     * GSON doesn't parse to record correctly with current version.
     * Records are marked as final and GSON doesn't like that.
     * When during a version update, the build fails because of this unit test, it will most likely mean that the step towards GSON can be done.
     */
    @Test
    fun testGSONParsing_whenJson_thenParseOkWithRecords() {
        val gson = Gson()
        val messageString =
            "[{\"status\": \"INTRANSIT\", \"vendorId\": \"2\", \"supplierId\": \"1\", \"name\": \"Monster wheels\", \"composition\": [{\"products\": [{\"quantity\": 2500000, \"productId\": 1}], \"containerId\": 1, \"packageId\": 546372}], \"id\": 1}]"
        val trainMerchandiseDtos = gson.fromJson(messageString, Array<TrainMerchandiseDto>::class.java)
        trainMerchandiseDtos.shouldNotBeNull()
        trainMerchandiseDtos.shouldHaveSize(1)
    }

    @Test
    @Throws(JsonProcessingException::class)
    fun testObjectMapperParsing_whenJson_thenParseOkWithRecords() {
        val objectMapper = ObjectMapper()
        val messageString =
            "[{\"status\": \"INTRANSIT\", \"vendorId\": \"2\", \"supplierId\": \"1\", \"name\": \"Monster wheels\", \"composition\": [{\"products\": [{\"quantity\": 2500000, \"productId\": 1}], \"containerId\": 1, \"packageId\": 546372}], \"id\": 1}]"
        val vehicleMerchandiseDtos = objectMapper.readValue(messageString, Array<TrainMerchandiseDto>::class.java)
        vehicleMerchandiseDtos.shouldNotBeNull()
        vehicleMerchandiseDtos.shouldHaveSize(1)
    }
}