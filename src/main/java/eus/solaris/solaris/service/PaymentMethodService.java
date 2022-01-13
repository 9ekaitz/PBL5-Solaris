package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.PaymentMethod;

public interface PaymentMethodService {

    public boolean save(PaymentMethod paymentMethod);

    public PaymentMethod findById(Long id);

    public void disable(PaymentMethod paymentMethod);
    
}
