package br.com.customer.orders.infrastructure.specification;

import br.com.customer.orders.userinterface.usecase.IRetrieveOrdersUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@DisplayName("OrderFilterSpecificationTest")
class OrderFilterSpecificationTest {
    private final OrderFilterSpecification specification = new OrderFilterSpecification();
    
    @Test
    @DisplayName("OrderFilterSpecification.apply")
    void apply() {
        var numberControl = "numberControl";
        var registrationDate = LocalDateTime.now();
        
        var filter = new IRetrieveOrdersUseCase.Filter(numberControl, registrationDate, registrationDate);
        var result = specification.apply(filter);

        Assertions.assertNotNull(result);
    }
}
