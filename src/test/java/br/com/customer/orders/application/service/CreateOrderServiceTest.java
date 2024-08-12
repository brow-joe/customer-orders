package br.com.customer.orders.application.service;

import br.com.customer.orders.application.dto.OrderDTO;
import br.com.customer.orders.application.dto.OrdersDTO;
import br.com.customer.orders.application.translator.OrderTranslator;
import br.com.customer.orders.domain.Order;
import br.com.customer.orders.domain.gateway.ISaveOrderGateway;
import br.com.customer.orders.userinterface.usecase.ICreateOrderUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

@DisplayName("CreateOrderServiceTest")
class CreateOrderServiceTest {
    private final OrderTranslator translator = Mockito.mock(OrderTranslator.class);
    private final ISaveOrderGateway gateway = Mockito.mock(ISaveOrderGateway.class);
    private final ICreateOrderUseCase service = new CreateOrderService(translator, gateway);
    
    @Test
    @DisplayName("CreateOrderService.execute")
    void execute() {
        var numberControl = "numberControl";
        var registrationDate = LocalDateTime.now();
        var name = "name";
        var value = 100.0;
        var quantity = 1;
        var customerCode = 1;
        
        var dto = new OrderDTO(numberControl, registrationDate, 
                name, value, quantity, customerCode
        );
        var order = new Order()
                .setNumberControl(numberControl)
                .setRegistrationDate(registrationDate)
                .setName(name)
                .setValue(value)
                .setQuantity(quantity)
                .setCustomerCode(customerCode);
        
        Mockito.when(translator.translate(dto))
                .thenReturn(order);
        
        service.execute(new OrdersDTO(List.of(dto)));
        Mockito.verify(gateway).save(List.of(order));
    }
}