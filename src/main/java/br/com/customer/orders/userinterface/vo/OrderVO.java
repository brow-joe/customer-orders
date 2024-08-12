package br.com.customer.orders.userinterface.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDateTime;

@XmlRootElement(name = "pedido")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderVO(
        @JsonProperty("numeroControle")
        @XmlElement(name = "numeroControle")
        String numberControl,

        @JsonProperty("dataCadastro")
        @XmlElement(name = "dataCadastro")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        LocalDateTime registrationDate,

        @JsonProperty("nome")
        @XmlElement(name = "nome")
        String name,

        @JsonProperty("valor")
        @XmlElement(name = "valor")
        Double value,

        @JsonProperty("quantidade")
        @XmlElement(name = "quantidade")
        Integer quantity,

        @JsonProperty("codigoCliente")
        @XmlElement(name = "codigoCliente")
        Integer customerCode,

        @JsonProperty("desconto")
        @XmlElement(name = "desconto")
        Double discount,

        @JsonProperty("valorTotal")
        @XmlElement(name = "valorTotal")
        Double amount
) { }