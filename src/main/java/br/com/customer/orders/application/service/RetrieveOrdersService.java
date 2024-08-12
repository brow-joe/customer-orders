package br.com.customer.orders.application.service;

import br.com.customer.orders.application.translator.OrderTranslator;
import br.com.customer.orders.domain.gateway.IRetrieveOrdersGateway;
import br.com.customer.orders.userinterface.usecase.IRetrieveOrdersUseCase;
import br.com.customer.orders.userinterface.vo.OrderVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RetrieveOrdersService implements IRetrieveOrdersUseCase {
    private final OrderTranslator translator;
    private final IRetrieveOrdersGateway gateway;

    public RetrieveOrdersService(OrderTranslator translator, IRetrieveOrdersGateway gateway) {
        this.translator = translator;
        this.gateway = gateway;
    }

    @Override
    public List<OrderVO> execute(final IRetrieveOrdersUseCase.Filter filter) {
        var orders = gateway.retrieve(filter);
        return translator.translate(orders);
    }
}
