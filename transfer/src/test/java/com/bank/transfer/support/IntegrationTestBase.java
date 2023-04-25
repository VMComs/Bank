package com.bank.transfer.support;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ContextConfiguration(initializers = Postgres.Initializer.class)
@Transactional
public abstract class IntegrationTestBase {
    @BeforeAll
    static void init() {
        Postgres.database.start();
    }
}
