package br.com.customer.orders.application.translator;

import br.com.customer.orders.application.dto.OrderDTO;
import br.com.customer.orders.domain.Order;
import br.com.customer.orders.infrastructure.mapper.OrderMapper;
import br.com.customer.orders.userinterface.vo.OrderVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderTranslator {
    private final OrderMapper mapper;

    public OrderTranslator(OrderMapper mapper) {
        this.mapper = mapper;
    }
    
    public Order translate(OrderDTO order) {
        return mapper.map(order);
    }
    
    public List<OrderVO> translate(List<Order> orders) {
        return orders.stream()
                .map(mapper::map)
                .toList();
    }
}
