package br.com.tcc.paymentservice.resources;

import br.com.tcc.paymentservice.dtos.PaymentRequestDTO;
import br.com.tcc.paymentservice.services.PaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    PaymentService service;

    @POST
    @Path("/process")
    public Response pay(PaymentRequestDTO dto) {
        service.processPaymentWithRetry(dto.orderId(), dto.amount(), dto.customerId());
        return Response.ok().build();
    }
}
