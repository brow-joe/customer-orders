package br.com.customer.orders.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDateTime;

@XmlRootElement(name = "pedido")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderDTO(
        @JsonProperty("numeroControle")
        @XmlElement(name = "numeroControle")
        @NotBlank(message = "O número de controle é obrigatório e não pode estar vazio.")
        String numberControl,

        @JsonProperty("dataCadastro")
        @XmlElement(name = "dataCadastro")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        LocalDateTime registrationDate,

        @JsonProperty("nome")
        @XmlElement(name = "nome")
        @NotBlank(message = "O nome do produto é obrigatório e não pode estar vazio.")
        String name,

        @JsonProperty("valor")
        @XmlElement(name = "valor")
        @NotNull(message = "O valor do produto é obrigatório.")
        @Positive(message = "O valor do produto deve ser um número positivo.")
        Double value,

        @JsonProperty("quantidade")
        @XmlElement(name = "quantidade")
        Integer quantity,

        @JsonProperty("codigoCliente")
        @XmlElement(name = "codigoCliente")
        @NotNull(message = "O código do cliente é obrigatório.")
        @Positive(message = "O código do cliente deve ser um número positivo.")
        Integer customerCode
) { }