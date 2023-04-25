package com.bank.authorization.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

class ErrorResponseTest {

    private ErrorResponse expectedResult;

    @BeforeEach
    void setUp() {
        expectedResult = new ErrorResponse("message", Timestamp.valueOf("2000-01-01 12:00:00"));
    }

    @Test
    void createResponse() {
        ErrorResponse actualResult = new ErrorResponse("message", Timestamp.valueOf("2000-01-01 12:00:00"));

        Assertions.assertThat(expectedResult).isEqualTo(actualResult);
    }
}
