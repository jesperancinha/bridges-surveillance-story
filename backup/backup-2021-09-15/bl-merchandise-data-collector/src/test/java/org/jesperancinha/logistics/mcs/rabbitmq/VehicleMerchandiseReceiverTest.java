package org.jesperancinha.logistics.mcs.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.jesperancinha.logistics.mcs.dto.VehicleMerchandiseDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class VehicleMerchandiseReceiverTest {

    /**
     * GSON doesn't parse to record correctly with current version.
     * Records are marked as final and GSON doesn't like that.
     * When during a version update, the build fails because of this unit test, it will most likely mean that the step towards GSON can be done.
     */
    @Test
    public void testGSONParsing_whenJson_thenParseOkWithRecords() {
        final var gson = new Gson();

        final var messageString = "[{\"status\": \"INTRANSIT\", \"vendorId\": \"2\", \"supplierId\": \"1\", \"name\": \"Monster wheels\", \"composition\": [{\"products\": [{\"quantity\": 2500000, \"productId\": 1}], \"containerId\": 1, \"packageId\": 546372}], \"id\": 1}]";

        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> gson.fromJson(messageString, VehicleMerchandiseDto[].class));
    }

    @Test
    public void testObjectMapperParsing_whenJson_thenParseOkWithRecords() throws JsonProcessingException {
        final var objectMapper = new ObjectMapper();

        final var messageString = "[{\"status\": \"INTRANSIT\", \"vendorId\": \"2\", \"supplierId\": \"1\", \"name\": \"Monster wheels\", \"composition\": [{\"products\": [{\"quantity\": 2500000, \"productId\": 1}], \"containerId\": 1, \"packageId\": 546372}], \"id\": 1}]";

        final VehicleMerchandiseDto[] vehicleMerchandiseDtos = objectMapper.readValue(messageString, VehicleMerchandiseDto[].class);

        assertThat(vehicleMerchandiseDtos).isNotNull();
        assertThat(vehicleMerchandiseDtos).hasSize(1);
    }

}