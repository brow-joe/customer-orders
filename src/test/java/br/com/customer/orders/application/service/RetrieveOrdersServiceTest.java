package br.com.customer.orders.application.service;

import br.com.customer.orders.application.translator.OrderTranslator;
import br.com.customer.orders.domain.Order;
import br.com.customer.orders.domain.gateway.IRetrieveOrdersGateway;
import br.com.customer.orders.userinterface.usecase.IRetrieveOrdersUseCase;
import br.com.customer.orders.userinterface.vo.OrderVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

@DisplayName("RetrieveOrdersServiceTest")
class RetrieveOrdersServiceTest {
    private final OrderTranslator translator = Mockito.mock(OrderTranslator.class);
    private final IRetrieveOrdersGateway gateway = Mockito.mock(IRetrieveOrdersGateway.class);
    private final IRetrieveOrdersUseCase service = new RetrieveOrdersService(translator, gateway);

    @Test
    @DisplayName("RetrieveOrdersService.execute")
    void execute() {
        var numberControl = "numberControl";
        var registrationDate = LocalDateTime.now();
        var name = "name";
        var value = 100.0;
        var quantity = 1;
        var customerCode = 1;
        var discount = 0.0;
        var amount = 100.0;
        
        var filter = new IRetrieveOrdersUseCase.Filter(
                numberControl, registrationDate, registrationDate
        );
        var order = new Order()
                .setNumberControl(numberControl)
                .setRegistrationDate(registrationDate)
                .setName(name)
                .setValue(value)
                .setQuantity(quantity)
                .setCustomerCode(customerCode)
                .setDiscount(discount)
                .setAmount(amount);
        var orders = List.of(order);
        
        var vo = new OrderVO(numberControl, registrationDate,
                name, value, quantity, customerCode, discount, amount);
        
        Mockito.when(gateway.retrieve(filter))
                .thenReturn(orders);
        Mockito.when(translator.translate(orders))
                .thenReturn(List.of(vo));
        
        var response = service.execute(filter);
        Assertions.assertFalse(response.isEmpty());
        Assertions.assertTrue(response.contains(vo));
    }
}