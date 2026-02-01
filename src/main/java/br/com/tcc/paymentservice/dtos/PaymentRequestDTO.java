package br.com.tcc.paymentservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequestDTO(
        @JsonProperty("orderId")
        @NotNull(message = "Order ID is mandatory")
        Long orderId,

        @JsonProperty("amount")
        @NotNull(message = "Amount is mandatory")
        BigDecimal amount,

        @JsonProperty("customerId")
        @NotNull(message = "Customer ID is mandatory")
        Long customerId
) {}
