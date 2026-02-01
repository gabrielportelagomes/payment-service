package br.com.tcc.paymentservice.dtos;

import java.math.BigDecimal;

public record InventoryReservedEvent(
        Long orderId,
        Long productId,
        Integer quantity,
        Long customerId,
        BigDecimal amount
) {}
