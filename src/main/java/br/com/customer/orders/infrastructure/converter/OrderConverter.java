package br.com.customer.orders.infrastructure.converter;

import br.com.customer.orders.domain.Order;
import br.com.customer.orders.infrastructure.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderConverter {
    OrderEntity convert(Order order);
    
    Order convert(OrderEntity order);
    
    default List<Order> convert(List<OrderEntity> orders) {
        return orders.stream()
                .map(this::convert)
                .toList();
    }
}