package eus.solaris.solaris.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.domain.Province;
import eus.solaris.solaris.repository.AddressRepository;
import eus.solaris.solaris.service.impl.AddressServiceImpl;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @InjectMocks
    private AddressServiceImpl addressServiceImpl;

    @Mock
    private AddressRepository addressRepository;

    @Test
    void saveTest(){
        Address address = new Address(1L, new Country(), new Province(), "Vitoria", "01008", "Pintor Clemente Arraiz", "680728473", null, true, true, 1);

        when(addressRepository.save(address)).thenReturn(address);
        assertEquals(address, addressServiceImpl.save(address));
    }

    @Test
    void findByIdTest(){
        Address address = new Address(1L, new Country(), new Province(), "Vitoria", "01008", "Pintor Clemente Arraiz", "680728473", null, true, true, 1);

        when(addressRepository.getById(1L)).thenReturn(address);
        assertEquals(address, addressServiceImpl.findById(1L));
    }

    @Test
    void disabledTest(){
        Address address1 = new Address(1L, new Country(), new Province(), "Vitoria", "01008", "Pintor Clemente Arraiz", "680728473", null, true, true, 1);
        Address address2 = new Address(1L, new Country(), new Province(), "Vitoria", "01008", "Pintor Clemente Arraiz", "680728473", null, false, false, 1);

        when(addressRepository.save(address1)).thenReturn(address1);
        assertEquals(address2, addressServiceImpl.disable(address1));
    }
}
