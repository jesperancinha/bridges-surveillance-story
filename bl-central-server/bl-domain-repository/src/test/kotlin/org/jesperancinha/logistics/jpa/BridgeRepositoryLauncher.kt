package org.jesperancinha.logistics.jpa

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Created by jofisaes on 25/08/2021
 */
@SpringBootApplication
@EnableJpaRepositories
class BridgeRepositoryLauncher {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(BridgeRepositoryLauncher::class.java)
        }
    }
}