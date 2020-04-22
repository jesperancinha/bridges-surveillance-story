package org.jesperancinha.logistics.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.jesperancinha.logistics.jpa.repositories")
@EntityScan("org.jesperancinha.logistics.jpa.model")

public class BridgeLogisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BridgeLogisticsApplication.class, args);
    }
}
