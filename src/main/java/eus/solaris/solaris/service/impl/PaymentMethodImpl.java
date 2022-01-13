package eus.solaris.solaris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.repository.PaymentMethodRepository;
import eus.solaris.solaris.service.PaymentMethodService;

@Service
public class PaymentMethodImpl implements PaymentMethodService{

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Override
    public boolean save(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod) != null;
    }

    @Override
    public PaymentMethod findById(Long id) {
        return paymentMethodRepository.findById(id).get();
    }

    @Override
    public void disable(PaymentMethod paymentMethod) {
        paymentMethod.setEnabled(false);
        paymentMethodRepository.save(paymentMethod);        
    }
    
}
