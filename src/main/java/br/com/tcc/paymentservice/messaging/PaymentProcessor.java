package br.com.tcc.paymentservice.messaging;

import br.com.tcc.paymentservice.dtos.InventoryReservedEvent;
import br.com.tcc.paymentservice.dtos.PaymentApprovedEvent;
import br.com.tcc.paymentservice.dtos.PaymentFailedEvent;
import br.com.tcc.paymentservice.enums.PaymentStatus;
import br.com.tcc.paymentservice.services.PaymentService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class PaymentProcessor {

    private static final Logger LOG = Logger.getLogger(PaymentProcessor.class);

    @Inject
    PaymentService paymentService;

    @Inject
    @Channel("payment-processed") Emitter<PaymentApprovedEvent> successEmitter;

    @Inject
    @Channel("payment-failed") Emitter<PaymentFailedEvent> failEmitter;

    @Incoming("inventory-reserved-in")
    public void processInventoryReserved(InventoryReservedEvent event) {
        LOG.infof("Processando pagamento do pedido %s", event.orderId());

        try {

            paymentService.processPaymentWithRetry(
                    event.orderId(),
                    event.amount(),
                    event.customerId()
            );

            successEmitter.send(
                    new PaymentApprovedEvent(event.orderId(), PaymentStatus.SUCCESS)
            );

            LOG.infof("Pagamento aprovado para pedido {}", event.orderId());

        } catch (WebApplicationException e) {
            LOG.warnf("Pagamento recusado para pedido %s", event.orderId());

            failEmitter.send(
                    new PaymentFailedEvent(
                            event.orderId(),
                            event.productId(),
                            event.quantity()
                    )
            );
        } catch (Exception e) {
            LOG.error("Erro técnico ao processar pagamento", e);
            throw e;
        }
    }
}
