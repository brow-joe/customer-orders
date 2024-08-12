package br.com.customer.orders.userinterface.usecase;

import br.com.customer.orders.application.dto.OrdersDTO;

@FunctionalInterface
public interface ICreateOrderUseCase {
    void execute(OrdersDTO orders);
}