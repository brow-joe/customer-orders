package br.com.customer.orders.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "ordem")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrdersDTO(
        @JsonProperty("pedidos")
        @XmlElementWrapper(name = "pedidos")
        @XmlElement(name = "pedido")
        @NotEmpty(message = "A lista de pedidos não pode ser vazia.")
        @Size(max = 10, message = "A lista de pedidos não pode ultrapassar 10 pedidos")
        @Valid
        List<OrderDTO> orders
) { }