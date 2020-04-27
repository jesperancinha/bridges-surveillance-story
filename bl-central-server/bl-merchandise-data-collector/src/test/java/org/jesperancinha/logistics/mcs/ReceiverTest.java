package org.jesperancinha.logistics.mcs;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.Statement;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class ReceiverTest {

    private static EmbeddedPostgres embeddedPostgres;

    @BeforeAll
    public static void initialise() throws Exception {
        if (embeddedPostgres == null) {
            embeddedPostgres = EmbeddedPostgres.builder()
                .setPort(5433)
                .start();

            try (Connection conn = embeddedPostgres.getPostgresDatabase()
                .getConnection()) {
                Statement statement = conn.createStatement();
                statement.execute("CREATE DATABASE bllogistics");
            }
        }
    }

    @Test
    public void receiveMessage() {
        System.out.println("OK");
    }
}
