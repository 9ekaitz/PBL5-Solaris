package eus.solaris.solaris.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.repository.PaymentMethodRepository;
import eus.solaris.solaris.service.impl.PaymentMethodServiceImpl;

@ExtendWith(MockitoExtension.class)
class PaymentMethodImplTest {

    @InjectMocks
    private PaymentMethodServiceImpl paymentMethodImpl;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Test
    void saveTest(){
        PaymentMethod paymentMethod = createPaymentMethod();

        when(paymentMethodRepository.save(paymentMethod)).thenReturn(paymentMethod);
        assertEquals(paymentMethod, paymentMethodImpl.save(paymentMethod));
    }

    @Test
    void findByIdTest(){
        PaymentMethod paymentMethod = createPaymentMethod();

        when(paymentMethodRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(paymentMethod));
        assertEquals(paymentMethod, paymentMethodImpl.findById(1L));
    }

    @Test
    void disabledTest(){
        PaymentMethod paymentMethod1 = createPaymentMethod();
        PaymentMethod paymentMethod2 = createPaymentMethod();
        paymentMethod2.setEnabled(false);
        paymentMethod2.setDefaultMethod(false);

        when(paymentMethodRepository.save(paymentMethod1)).thenReturn(paymentMethod1);
        assertEquals(paymentMethod2, paymentMethodImpl.disable(paymentMethod1));
    }

    private PaymentMethod createPaymentMethod() {
        return new PaymentMethod(1L, null, "Aritz Domaika Peirats", "5555666677778888", 1L, 2027L, "222", true, true, 1);
    }
    
}
