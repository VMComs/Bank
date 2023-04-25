package com.bank.history.handler;

import com.bank.history.exception.HistoryNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * тестирование handler
 */
public class HandlerTest {

    private HistoryExceptionHandler exceptionHandler = new HistoryExceptionHandler();

    @Test
    void ExceptionTest() {
        ResponseEntity<String> response = exceptionHandler.handler(new HistoryNotFoundException());

        assertThat(response.getStatusCodeValue()).isEqualTo((HttpStatus.NOT_FOUND).value());
    }
}
