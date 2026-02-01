package br.com.tcc.paymentservice.dtos;

public record PaymentFailedEvent(Long orderId, Long productId, Integer quantity) {}

