package br.com.tcc.paymentservice.services;

import br.com.tcc.paymentservice.enums.PaymentStatus;
import br.com.tcc.paymentservice.models.Payment;
import br.com.tcc.paymentservice.repositories.PaymentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.faulttolerance.Retry;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationScoped
public class PaymentService {

    @Inject
    PaymentRepository repository;

    @Inject
    PaymentService self;

    public void processPaymentWithRetry(Long orderId, BigDecimal amount, Long customerId) {
        self.execute(orderId, amount, customerId); // chama via proxy
    }

    @Retry(maxRetries = 3, delay = 2000, abortOn = WebApplicationException.class)
    void execute(Long orderId, BigDecimal amount, Long customerId) {
        processPayment(orderId, amount, customerId);
    }

    @Transactional
    public void processPayment(Long orderId, BigDecimal amount, Long customerId) {
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setCustomerId(customerId);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDateTime.now());

        if (amount.compareTo(BigDecimal.valueOf(1000)) > 0) {
            payment.setStatus(PaymentStatus.FAILED);
            repository.persist(payment);
            throw new WebApplicationException("Pagamento recusado", 402);
        }

        payment.setStatus(PaymentStatus.SUCCESS);
        repository.persist(payment);
    }
}
