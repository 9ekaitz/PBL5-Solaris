package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.PaymentMethod;

public interface PaymentMethodService {

    public PaymentMethod save(PaymentMethod paymentMethod);

    public PaymentMethod findById(Long id);

    public PaymentMethod disable(PaymentMethod paymentMethod);
    
}
