package br.com.customer.orders.infrastructure.adapter;

import br.com.customer.orders.application.exception.BusinessException;
import br.com.customer.orders.domain.Order;
import br.com.customer.orders.infrastructure.converter.OrderConverter;
import br.com.customer.orders.infrastructure.entity.OrderEntity;
import br.com.customer.orders.infrastructure.repository.ClientRepository;
import br.com.customer.orders.infrastructure.repository.OrderRepository;
import br.com.customer.orders.infrastructure.specification.OrderFilterSpecification;
import br.com.customer.orders.userinterface.usecase.IRetrieveOrdersUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

@DisplayName("OrderAdapterTest")
class OrderAdapterTest {
    private final OrderConverter converter = Mockito.mock(OrderConverter.class);
    private final OrderFilterSpecification specification = new OrderFilterSpecification();
    private final ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
    private final OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private final OrderAdapter adapter = new OrderAdapter(converter, specification, clientRepository, orderRepository);

    private final Long id = 1L;
    private final String numberControl = "numberControl";
    private final LocalDateTime registrationDate = LocalDateTime.now();
    private final String name = "name";
    private final Double value = 100.0;
    private final Integer quantity = 1;
    private final Integer customerCode = 1;
    private final Double discount = 0.0;
    private final Double amount = 100.0;
    
    @Test
    @DisplayName("OrderAdapter.save")
    void save() {
        var order = new Order()
                .setNumberControl(numberControl)
                .setRegistrationDate(registrationDate)
                .setName(name)
                .setValue(value)
                .setQuantity(quantity)
                .setCustomerCode(customerCode)
                .setDiscount(discount)
                .setAmount(amount);
        
        var entity = new OrderEntity();
        entity.setId(id);
        entity.setNumberControl(numberControl);
        entity.setRegistrationDate(registrationDate);
        entity.setName(name);
        entity.setValue(value);
        entity.setQuantity(quantity);
        entity.setCustomerCode(customerCode);
        entity.setDiscount(discount);
        entity.setAmount(amount);
        
        Mockito.when(orderRepository.existsByNumberControl(numberControl))
                .thenReturn(false);
        Mockito.when(clientRepository.existsById(customerCode))
                .thenReturn(true);
        Mockito.when(converter.convert(order))
                .thenReturn(entity);
        
        adapter.save(List.of(order));
        Mockito.verify(orderRepository).save(entity);
    }

    @Test
    @DisplayName("Fail OrderAdapter.save - Número de controle já cadastrado")
    void failSaveExistentNumberControl() {
        var order = new Order()
                .setNumberControl(numberControl)
                .setRegistrationDate(registrationDate)
                .setName(name)
                .setValue(value)
                .setQuantity(quantity)
                .setCustomerCode(customerCode)
                .setDiscount(discount)
                .setAmount(amount);

        Mockito.when(orderRepository.existsByNumberControl(numberControl))
                .thenReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> {
            adapter.save(List.of(order));
        });
    }

    @Test
    @DisplayName("Fail OrderAdapter.save - Cliente não encontrado")
    void failSaveClientNotFound() {
        var order = new Order()
                .setNumberControl(numberControl)
                .setRegistrationDate(registrationDate)
                .setName(name)
                .setValue(value)
                .setQuantity(quantity)
                .setCustomerCode(customerCode)
                .setDiscount(discount)
                .setAmount(amount);

        Mockito.when(orderRepository.existsByNumberControl(numberControl))
                .thenReturn(false);
        Mockito.when(clientRepository.existsById(customerCode))
                .thenReturn(false);

        Assertions.assertThrows(BusinessException.class, () -> {
            adapter.save(List.of(order));
        });
    }
    
    @Test
    @DisplayName("OrderAdapter.retrieve")
    void retrieve() {
        var filter = new IRetrieveOrdersUseCase.Filter(numberControl, registrationDate, registrationDate);

        var entity = new OrderEntity();
        entity.setId(id);
        entity.setNumberControl(numberControl);
        entity.setRegistrationDate(registrationDate);
        entity.setName(name);
        entity.setValue(value);
        entity.setQuantity(quantity);
        entity.setCustomerCode(customerCode);
        entity.setDiscount(discount);
        entity.setAmount(amount);

        var order = new Order()
                .setNumberControl(numberControl)
                .setRegistrationDate(registrationDate)
                .setName(name)
                .setValue(value)
                .setQuantity(quantity)
                .setCustomerCode(customerCode)
                .setDiscount(discount)
                .setAmount(amount);
        
        Mockito.when(orderRepository.findAll(Mockito.any(Specification.class)))
                .thenReturn(List.of(entity));
        Mockito.when(converter.convert(Mockito.anyList()))
                .thenReturn(List.of(order));
        
        var result = adapter.retrieve(filter);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertTrue(result.contains(order));
    }
}
