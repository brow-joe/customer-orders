package br.com.customer.orders.application.translator;

import br.com.customer.orders.application.dto.OrderDTO;
import br.com.customer.orders.domain.Order;
import br.com.customer.orders.infrastructure.mapper.OrderMapper;
import br.com.customer.orders.userinterface.vo.OrderVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

@DisplayName("OrderTranslatorTest")
class OrderTranslatorTest {
    private final OrderMapper mapper = Mockito.mock(OrderMapper.class);
    private final OrderTranslator translator = new OrderTranslator(mapper);

    private final String numberControl = "numberControl";
    private final LocalDateTime registrationDate = LocalDateTime.now();
    private final String name = "name";
    private final Double value = 100.0;
    private final Integer quantity = 1;
    private final Integer customerCode = 1;
    
    @Test
    @DisplayName("OrderTranslator.translate")
    void translate() {
        var source = new OrderDTO(numberControl, registrationDate, 
                name, value, quantity, customerCode);

        var target = new Order()
                .setNumberControl(numberControl)
                .setRegistrationDate(registrationDate)
                .setName(name)
                .setValue(value)
                .setQuantity(quantity)
                .setCustomerCode(customerCode);
        
        Mockito.when(mapper.map(source))
                .thenReturn(target);
        
        var result = translator.translate(source);
        Assertions.assertEquals(target, result);
    }

    @Test
    @DisplayName("OrderTranslator.translateList")
    void translateList() {
        var discount = 0.0;
        var amount = 100.0;
        
        var source = new Order()
                .setNumberControl(numberControl)
                .setRegistrationDate(registrationDate)
                .setName(name)
                .setValue(value)
                .setQuantity(quantity)
                .setCustomerCode(customerCode)
                .setDiscount(discount)
                .setAmount(amount);

        var target = new OrderVO(numberControl, registrationDate,
                name, value, quantity, customerCode, discount, amount);
        
        Mockito.when(mapper.map(source))
                .thenReturn(target);
        
        var result = translator.translate(List.of(source));
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertTrue(result.contains(target));
    }
}
