package br.com.customer.orders.userinterface.controller;

import br.com.customer.orders.application.dto.OrderDTO;
import br.com.customer.orders.application.dto.OrdersDTO;
import br.com.customer.orders.infrastructure.configuration.SecurityConfiguration;
import br.com.customer.orders.infrastructure.configuration.WebConfiguration;
import br.com.customer.orders.userinterface.usecase.ICreateOrderUseCase;
import br.com.customer.orders.userinterface.usecase.IRetrieveOrdersUseCase;
import br.com.customer.orders.userinterface.vo.OrderVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ActiveProfiles("test")
@DisplayName("OrdersControllerTest")
@Import({ SecurityConfiguration.class, WebConfiguration.class })
@WebMvcTest(controllers = OrdersController.class)
class OrdersControllerTest {
    private static final String AUTHORIZATION = "Basic dGVzdDp0ZXN0";
    
    @InjectMocks
    private OrdersController ordersController;

    @MockBean
    private ICreateOrderUseCase createOrderUseCase;

    @MockBean
    private IRetrieveOrdersUseCase retrieveOrdersUseCase;

    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private MockMvc mockMvc;
    
    private final String numberControl = "numberControl";
    private final LocalDateTime registrationDate = LocalDateTime.now();
    private final String name = "name";
    private final Double value = 100.0;
    private final Integer quantity = 1;
    private final Integer customerCode = 1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("OrdersController.create")
    void create() throws Exception {
        var order = new OrderDTO(numberControl, registrationDate, 
                name, value, quantity, customerCode
        );
        var dto = new OrdersDTO(List.of(order));
        
        var request = mapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.post(OrdersController.PATH)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("fail OrdersController.create - A lista de pedidos não pode ser vazia.")
    void failCreateOrdesIsEmpty() throws Exception {
        var order = new OrderDTO(numberControl, registrationDate,
                name, value, quantity, customerCode
        );
        var dto = new OrdersDTO(Stream.generate(() -> order)
                .limit(11)
                .collect(Collectors.toList()));

        var request = mapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.post(OrdersController.PATH)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("fail OrdersController.create - A lista de pedidos não pode ultrapassar 10 pedidos")
    void failCreateMaxOrdersExceeded() throws Exception {
        var dto = new OrdersDTO(List.of());

        var request = mapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.post(OrdersController.PATH)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("fail OrdersController.create - O número de controle é obrigatório e não pode estar vazio.")
    void failCreateNumberControlIsEmpty() throws Exception {
        var order = new OrderDTO(null, registrationDate,
                name, value, quantity, customerCode
        );
        var dto = new OrdersDTO(List.of(order));

        var request = mapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.post(OrdersController.PATH)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("fail OrdersController.create - O nome do produto é obrigatório e não pode estar vazio.")
    void failCreateNameIsEmpty() throws Exception {
        var order = new OrderDTO(numberControl, registrationDate,
                null, value, quantity, customerCode
        );
        var dto = new OrdersDTO(List.of(order));

        var request = mapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.post(OrdersController.PATH)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("fail OrdersController.create - O valor do produto é obrigatório.")
    void failCreateValueIsEmpty() throws Exception {
        var order = new OrderDTO(numberControl, registrationDate,
                name, null, quantity, customerCode
        );
        var dto = new OrdersDTO(List.of(order));

        var request = mapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.post(OrdersController.PATH)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("fail OrdersController.create - O valor do produto deve ser um número positivo.")
    void failCreateValueIsNegative() throws Exception {
        var value = -1.0;
        var order = new OrderDTO(numberControl, registrationDate,
                name, value, quantity, customerCode
        );
        var dto = new OrdersDTO(List.of(order));

        var request = mapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.post(OrdersController.PATH)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("fail OrdersController.create - O código do cliente é obrigatório.")
    void failCreateCustomerCodeIsEmpty() throws Exception {
        var order = new OrderDTO(numberControl, registrationDate,
                name, value, quantity, null
        );
        var dto = new OrdersDTO(List.of(order));

        var request = mapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.post(OrdersController.PATH)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("fail OrdersController.create - O código do cliente deve ser um número positivo.")
    void failCreateCustomerCodeIsNegative() throws Exception {
        var customerCode = -1;
        var order = new OrderDTO(numberControl, registrationDate,
                name, value, quantity, customerCode
        );
        var dto = new OrdersDTO(List.of(order));

        var request = mapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders.post(OrdersController.PATH)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("OrdersController.retrieve")
    void retrieve() throws Exception {
        var discount = 0.0;
        var amount = 100.0;
        var order = new OrderVO(numberControl, registrationDate,
                name, value, quantity, customerCode, discount, amount);

        Mockito.when(retrieveOrdersUseCase.execute(Mockito.any()))
                .thenReturn(List.of(order));

        mockMvc.perform(MockMvcRequestBuilders.get(OrdersController.PATH)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("OrdersController.retrieve - With full parameters")
    void retrieveWithFilter() throws Exception {
        var discount = 0.0;
        var amount = 100.0;
        var order = new OrderVO(numberControl, registrationDate,
                name, value, quantity, customerCode, discount, amount);

        Mockito.when(retrieveOrdersUseCase.execute(Mockito.any()))
                .thenReturn(List.of(order));

        var registrationDate = "2024-08-10T20:13:20.048Z";
        mockMvc.perform(MockMvcRequestBuilders.get(OrdersController.PATH)
                        .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION)
                        .param("numeroControle", numberControl, 
                                "dataCadastroDe", registrationDate, 
                                "dataCadastroAte", registrationDate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());
    }
}
