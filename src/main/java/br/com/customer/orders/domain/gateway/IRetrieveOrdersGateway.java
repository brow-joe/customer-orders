package br.com.customer.orders.domain.gateway;

import br.com.customer.orders.domain.Order;

import java.time.LocalDateTime;
import java.util.List;

@FunctionalInterface
public interface IRetrieveOrdersGateway {
    List<Order> retrieve(Filter filter);

    interface Filter {
        String numberControl();
        LocalDateTime registrationDateStart();
        LocalDateTime registrationDateEnd();
    }
}