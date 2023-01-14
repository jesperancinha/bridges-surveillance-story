package org.jesperancinha.logistics.mcs

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.sql.DriverManager

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@Testcontainers
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

        @JvmStatic
        @BeforeAll
        @Throws(Exception::class)
        fun initialise(): Unit {
            DriverManager.getConnection(postgreSQLContainer.jdbcUrl, "postgres", "admin").use { conn ->
                val statement = conn.createStatement()
                statement.execute("CREATE DATABASE bllogistics")
            }
        }
    }
}