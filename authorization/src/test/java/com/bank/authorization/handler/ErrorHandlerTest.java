package com.bank.authorization.handler;

import com.bank.authorization.exception.AuditNotCreatedException;
import com.bank.authorization.exception.AuditNotFoundException;
import com.bank.authorization.exception.RoleNotCreatedException;
import com.bank.authorization.exception.RoleNotFoundException;
import com.bank.authorization.exception.UserNotCreatedException;
import com.bank.authorization.exception.UserNotFoundException;
import com.bank.authorization.util.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorHandlerTest {

    private final ErrorHandler errorHandler = new ErrorHandler();

    @Test
    void testUserHandlerNotFoundException() {

        final ResponseEntity<ErrorResponse> result = errorHandler.handlerException(new UserNotFoundException());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testRoleHandlerNotFoundException() {

        final ResponseEntity<ErrorResponse> result = errorHandler.handlerException(new RoleNotFoundException());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testAuditHandlerNotFoundException() {

        final ResponseEntity<ErrorResponse> result = errorHandler.handlerException(new AuditNotFoundException());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUserHandlerNotCreatedException() {

        final ResponseEntity<ErrorResponse> result = errorHandler.handlerException(new UserNotCreatedException("Error"));

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testRoleHandlerNotCreatedException() {

        final ResponseEntity<ErrorResponse> result = errorHandler.handlerException(new RoleNotCreatedException("Error"));

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testAuditHandlerNotCreatedException() {

        final ResponseEntity<ErrorResponse> result = errorHandler.handlerException(new AuditNotCreatedException("Error"));

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
