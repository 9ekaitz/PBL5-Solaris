package eus.solaris.solaris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.repository.PaymentMethodRepository;
import eus.solaris.solaris.service.PaymentMethodService;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService{

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentMethod save(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethod findById(Long id) {
        return paymentMethodRepository.findById(id).orElse(null);
    }

    @Override
    public PaymentMethod disable(PaymentMethod paymentMethod) {
        paymentMethod.setDefaultMethod(false);
        paymentMethod.setEnabled(false);
        return paymentMethodRepository.save(paymentMethod);        
    }
    
}
