package br.com.customer.orders.userinterface.controller.handler;

import br.com.customer.orders.application.exception.BusinessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionControlHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionControlHandler.class);
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Problem> handle(ConstraintViolationException e) {
        logger.error("key=handle, error={}", e.getMessage(), e);
        var errors = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        error -> error.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));
        return handle(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Problem> handle(BusinessException e) {
        var error = e.getMessage();
        logger.error("key=handle, error={}", error, e);
        return handle(HttpStatus.BAD_REQUEST, error);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Problem> handle(Throwable e) {
        var error = e.getMessage();
        logger.error("key=handle, error={}", error, e);
        return handle(HttpStatus.INTERNAL_SERVER_ERROR, error);
    }
    
    private <T> ResponseEntity<Problem> handle(HttpStatus status, T body) {
        var error = Objects.isNull(body) ? Problem.create() : Problem.create(body);
        return ResponseEntity.status(status).body(error);
    }
}