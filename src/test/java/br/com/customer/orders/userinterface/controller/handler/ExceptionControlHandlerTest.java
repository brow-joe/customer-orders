package br.com.customer.orders.userinterface.controller.handler;

import br.com.customer.orders.application.exception.BusinessException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashSet;

@DisplayName("ExceptionControlHandlerTest")
class ExceptionControlHandlerTest {
    private final ExceptionControlHandler handler = new ExceptionControlHandler();
    
    @Test
    @DisplayName("ExceptionControlHandler.handle - ConstraintViolationException")
    void constraintViolationException() {
        var exception = new ConstraintViolationException(new HashSet<>());
        var result = handler.handle(exception);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("ExceptionControlHandler.handle - BusinessException")
    void businessException() {
        var exception = new BusinessException("unit test");
        var result = handler.handle(exception);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("ExceptionControlHandler.handle - Throwable")
    void throwable() {
        var exception = new Exception();
        var result = handler.handle(exception);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}
