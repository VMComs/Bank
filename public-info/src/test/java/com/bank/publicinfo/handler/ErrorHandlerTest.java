package com.bank.publicinfo.handler;

import com.bank.publicinfo.exception.NotExecutedException;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.util.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.bank.publicinfo.handler.ErrorHandler.notExecutedException;
import static com.bank.publicinfo.handler.ErrorHandler.notFoundException;

class ErrorHandlerTest {

    @Test
    void testNotFoundException() {

        final ResponseEntity<ErrorResponse> result = notFoundException(new NotFoundException());

        assert(result.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }

    @Test
    void testNotExecutedException() {

        final ResponseEntity<ErrorResponse> result = notExecutedException(new NotExecutedException());

        assert(result.getStatusCode().equals(HttpStatus.BAD_REQUEST));
    }
}
