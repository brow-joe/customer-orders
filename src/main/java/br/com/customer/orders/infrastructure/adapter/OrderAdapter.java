package br.com.customer.orders.infrastructure.adapter;

import br.com.customer.orders.application.exception.BusinessException;
import br.com.customer.orders.domain.Order;
import br.com.customer.orders.domain.gateway.IRetrieveOrdersGateway;
import br.com.customer.orders.domain.gateway.ISaveOrderGateway;
import br.com.customer.orders.infrastructure.converter.OrderConverter;
import br.com.customer.orders.infrastructure.repository.ClientRepository;
import br.com.customer.orders.infrastructure.repository.OrderRepository;
import br.com.customer.orders.infrastructure.specification.OrderFilterSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderAdapter implements ISaveOrderGateway, IRetrieveOrdersGateway {
    private static final Logger logger = LoggerFactory.getLogger(OrderAdapter.class);

    private final OrderConverter converter;
    private final OrderFilterSpecification specification;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    public OrderAdapter(OrderConverter converter, OrderFilterSpecification specification,
        ClientRepository clientRepository, OrderRepository orderRepository) {
            this.converter = converter;
            this.specification = specification;
            this.clientRepository = clientRepository;
            this.orderRepository = orderRepository;
    }

    @Override
    public void save(List<Order> orders) {
        logger.debug("key=save, orders={}", orders);
        orders.forEach(this::save);
    }

    @Override
    public List<Order> retrieve(Filter filter) {
        logger.debug("key=retrieve, filter={}", filter);
        return converter.convert(orderRepository.findAll(
                specification.apply(filter)
        ));
    }
    
    private void save(Order order) {
        if (orderRepository.existsByNumberControl(order.getNumberControl())) {
            throw new BusinessException("Número de controle já cadastrado");
        }
        if (!clientRepository.existsById(order.getCustomerCode())) {
            throw new BusinessException("Cliente não encontrado");
        }
        var entity = converter.convert(order);
        orderRepository.save(entity);
    }
}