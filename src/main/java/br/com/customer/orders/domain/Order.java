package br.com.customer.orders.domain;

import java.time.LocalDateTime;

public class Order {
    private String numberControl;
    private LocalDateTime registrationDate;
    private String name;
    private Double value;
    private Integer quantity;
    private Integer customerCode;
    private Double discount;
    private Double amount;

    public String getNumberControl() {
        return numberControl;
    }

    public Order setNumberControl(String numberControl) {
        this.numberControl = numberControl;
        return this;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public Order setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public Order setName(String name) {
        this.name = name;
        return this;
    }

    public Double getValue() {
        return value;
    }

    public Order setValue(Double value) {
        this.value = value;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Integer getCustomerCode() {
        return customerCode;
    }

    public Order setCustomerCode(Integer customerCode) {
        this.customerCode = customerCode;
        return this;
    }

    public Double getDiscount() {
        return discount;
    }

    public Order setDiscount(Double discount) {
        this.discount = discount;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public Order setAmount(Double amount) {
        this.amount = amount;
        return this;
    }
}