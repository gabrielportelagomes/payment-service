package br.com.tcc.paymentservice.dtos;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class InventoryReservedDeserializer extends ObjectMapperDeserializer<InventoryReservedEvent> {
    public InventoryReservedDeserializer() {
        super(InventoryReservedEvent.class);
    }
}
