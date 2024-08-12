package br.com.customer.orders.userinterface.controller;

import br.com.customer.orders.application.dto.OrdersDTO;
import br.com.customer.orders.userinterface.usecase.ICreateOrderUseCase;
import br.com.customer.orders.userinterface.usecase.IRetrieveOrdersUseCase;
import br.com.customer.orders.userinterface.vo.OrderVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Pedidos", description = "Pedidos dos Clientes")
@RestController
@RequestMapping(OrdersController.PATH)
public class OrdersController extends AbstractedController<OrdersController>{
    protected static final String PATH = "/api/v1/orders";
    
    private final ICreateOrderUseCase createOrderUseCase;
    private final IRetrieveOrdersUseCase retrieveOrdersUseCase;
    
    public OrdersController(ICreateOrderUseCase createOrderUseCase, IRetrieveOrdersUseCase retrieveOrdersUseCase) {
        super(OrdersController.class);
        this.createOrderUseCase = createOrderUseCase;
        this.retrieveOrdersUseCase = retrieveOrdersUseCase;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Void> create(@RequestBody OrdersDTO orders) {
        validate(orders);
        createOrderUseCase.execute(orders);
        var link = linkOf(linked.create(orders));
        return ResponseEntity.created(link.toUri()).build();
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<CollectionModel<OrderVO>> retrieve(@ParameterObject IRetrieveOrdersUseCase.Filter filter) {
        var content = retrieveOrdersUseCase.execute(filter);
        var link = linkOf(linked.retrieve(filter));
        var response = CollectionModel.of(content, link);
        return ResponseEntity.ok(response);
    }
}