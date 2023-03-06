package org.jesperancinha.logistics.mcs

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.sql.DriverManager

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@Testcontainers
@ActiveProfiles("test")
class TrainMerchandiseReceiverTest {
    @Test
    fun receiveMessage() {
        println("OK")
    }

    companion object {
        @Container
        private val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres")
            .withUsername("postgres")
            .withPassword("admin")
            .withDatabaseName("bllogistics")

        init {
            postgreSQLContainer.start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add(
                "spring.datasource.url"
            ) { "jdbc:postgresql://localhost:${postgreSQLContainer.firstMappedPort}/bllogistics" }
        }
    }
}