package br.com.customer.orders.userinterface.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public abstract class AbstractedController<T> {
    protected final T linked;
    private final Validator validator;

    public AbstractedController(Class<T> root) {
        this.linked = WebMvcLinkBuilder.methodOn(root);
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    protected Link linkOf(Object method) {
        return WebMvcLinkBuilder.linkTo(
                method
        ).withSelfRel();
    }
    
    protected <T> void validate(T body) {
        var constraints = this.validator.validate(body);
        if (!constraints.isEmpty()) {
            throw new ConstraintViolationException(constraints);
        }
    }
}