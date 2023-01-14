package org.jesperancinha.logistics.web;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.jesperancinha.logistics.jpa.repositories")
@EntityScan("org.jesperancinha.logistics.jpa.dao")
@OpenAPIDefinition(
        info = @Info(title = "OpenAPI definition"),
        servers = {
                @Server(url = "${bl.server.url}/api/bridge/logistics",
                        description = "Server URL")
        }
)
public class BridgeLogisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BridgeLogisticsApplication.class, args);
    }
}
