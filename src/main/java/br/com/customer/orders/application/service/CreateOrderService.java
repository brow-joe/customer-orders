package br.com.customer.orders.application.service;

import br.com.customer.orders.application.dto.OrdersDTO;
import br.com.customer.orders.application.translator.OrderTranslator;
import br.com.customer.orders.domain.gateway.ISaveOrderGateway;
import br.com.customer.orders.userinterface.usecase.ICreateOrderUseCase;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderService implements ICreateOrderUseCase {
    private final OrderTranslator translator;
    private final ISaveOrderGateway gateway;

    public CreateOrderService(OrderTranslator translator, ISaveOrderGateway gateway) {
        this.translator = translator;
        this.gateway = gateway;
    }

    @Override
    public void execute(final OrdersDTO dto) {
        var orders = dto.orders().stream()
                .map(translator::translate)
                .toList();
        gateway.save(orders);
    }
}
