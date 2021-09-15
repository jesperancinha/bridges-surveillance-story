package org.jesperancinha.logistics.mcs;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@Testcontainers
public class TrainMerchandiseReceiverTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres")
                    .withUsername("postgres")
                    .withPassword("admin");

    @BeforeAll
    public static void initialise() throws Exception {
        try (Connection conn = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), "postgres","admin");
        ) {
            Statement statement = conn.createStatement();
            statement.execute("CREATE DATABASE bllogistics");
        }
    }


    @Test
    public void receiveMessage() {
        System.out.println("OK");
    }
}
