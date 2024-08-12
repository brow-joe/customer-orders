package br.com.customer.orders.infrastructure.mapper;

import br.com.customer.orders.application.dto.OrderDTO;
import br.com.customer.orders.domain.Order;
import br.com.customer.orders.userinterface.vo.OrderVO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    @Mapping(target = "registrationDate", source = "registrationDate", qualifiedByName = "mapRegistrationDate")
    @Mapping(target = "quantity", source = "quantity", qualifiedByName = "mapQuantity")
    Order map(OrderDTO order);
    
    OrderVO map(Order order);

    @Named("mapRegistrationDate")
    default LocalDateTime mapRegistrationDate(LocalDateTime registrationDate) {
        return (Objects.nonNull(registrationDate)) ? registrationDate : LocalDateTime.now();
    }

    @Named("mapQuantity")
    default Integer mapQuantity(Integer quantity) {
        return (Objects.nonNull(quantity) && quantity > 0) ? quantity : 1;
    }

    @AfterMapping
    default void afterMapping(OrderDTO dto, @MappingTarget Order order) {
        order.setDiscount(calculateDiscount(order));
        order.setAmount(calculateAmount(order));
    }

    default Double calculateDiscount(Order order) {
        var quantity = order.getQuantity();
        if (quantity >= 10) {
            return 0.10;
        } else if (quantity > 5) {
            return 0.05;
        }
        return 0.0;
    }

    default Double calculateAmount(Order order) {
        var value = BigDecimal.valueOf(order.getValue());
        var quantity = BigDecimal.valueOf(order.getQuantity());
        var totalValue = value.multiply(quantity);
        var discount = BigDecimal.valueOf(order.getDiscount());
        var amount = totalValue.multiply(BigDecimal.ONE.subtract(discount))
                .setScale(2, RoundingMode.HALF_UP);
        return amount.doubleValue();
    }
}