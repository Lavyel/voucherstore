package pl.msmet.voucherstore.sales.payment;

import pl.msmet.payu.exceptions.PayUException;

public class PaymentException extends IllegalStateException {
    public PaymentException(PayUException e) {
        super(e);
    }
}
