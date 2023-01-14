package org.jesperancinha.logistics.mcs

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("org.jesperancinha.logistics.jpa.dao")
@EntityScan("org.jesperancinha.logistics.jpa.dao")
class MerchandiseControlService

fun main(args: Array<String>) {
    SpringApplication.run(MerchandiseControlService::class.java, *args)
}