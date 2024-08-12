package br.com.customer.orders.domain.gateway;

import br.com.customer.orders.domain.Order;

import java.util.List;

@FunctionalInterface
public interface ISaveOrderGateway {
    void save(List<Order> orders);
}