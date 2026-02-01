package br.com.tcc.paymentservice.dtos;

import br.com.tcc.paymentservice.enums.PaymentStatus;

public record PaymentApprovedEvent(
        Long orderId,
        PaymentStatus status
) {}
