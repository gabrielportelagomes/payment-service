package br.com.tcc.paymentservice.repositories;

import br.com.tcc.paymentservice.models.Payment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<Payment> {}
