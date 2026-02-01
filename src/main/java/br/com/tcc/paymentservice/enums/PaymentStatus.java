package br.com.tcc.paymentservice.enums;

public enum PaymentStatus {

    /**
     * Pagamento processado e aprovado com sucesso.
     */
    SUCCESS,

    /**
     * Pagamento não pôde ser autorizado ou foi recusado.
     */
    FAILED
}
