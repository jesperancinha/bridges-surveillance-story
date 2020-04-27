package org.jesperancinha.logistics.sensor.collector.configuration;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataCollectorConfiguration {

    @Bean
    Gson gson() {
        return new Gson();
    }

}
