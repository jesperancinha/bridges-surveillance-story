package org.jesperancinha.logistics.sensor.collector.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataCollectorConfiguration {
    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}