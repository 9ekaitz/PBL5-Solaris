package eus.solaris.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.repository.PaymentMethodRepository;
import eus.solaris.solaris.service.impl.PaymentMethodImpl;

@ExtendWith(MockitoExtension.class)
class PaymentMethodImplTest {

    @InjectMocks
    private PaymentMethodImpl paymentMethodImpl;

    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Test
    void saveTest(){
        PaymentMethod paymentMethod = new PaymentMethod(1L, null, "Aritz Domaika Peirats", "5555666677778888", 1L, 2027L, "222", true, true, 1);

        when(paymentMethodRepository.save(paymentMethod)).thenReturn(paymentMethod);
        assertEquals(paymentMethod, paymentMethodImpl.save(paymentMethod));
    }

    @Test
    void findByIdTest(){
        PaymentMethod paymentMethod = new PaymentMethod(1L, null, "Aritz Domaika Peirats", "5555666677778888", 1L, 2027L, "222", true, true, 1);

        when(paymentMethodRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(paymentMethod));
        assertEquals(paymentMethod, paymentMethodImpl.findById(1L));
    }

    @Test
    void disabledTest(){
        PaymentMethod paymentMethod1 = new PaymentMethod(1L, null, "Aritz Domaika Peirats", "5555666677778888", 1L, 2027L, "222", true, true, 1);
        PaymentMethod paymentMethod2 = new PaymentMethod(1L, null, "Aritz Domaika Peirats", "5555666677778888", 1L, 2027L, "222", false, false, 1);

        when(paymentMethodRepository.save(paymentMethod1)).thenReturn(paymentMethod1);
        assertEquals(paymentMethod2, paymentMethodImpl.disable(paymentMethod1));
    }
    
}
