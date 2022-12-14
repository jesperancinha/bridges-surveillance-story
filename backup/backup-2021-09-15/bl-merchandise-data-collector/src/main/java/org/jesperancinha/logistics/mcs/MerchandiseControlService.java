package org.jesperancinha.logistics.mcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.jesperancinha.logistics.jpa.repositories")
@EntityScan("org.jesperancinha.logistics.jpa.model")
public class MerchandiseControlService {
    public static void main(String[] args) {
        SpringApplication.run(MerchandiseControlService.class, args);
    }
}
