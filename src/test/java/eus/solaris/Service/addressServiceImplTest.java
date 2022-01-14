package eus.solaris.Service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.mock.mockito.MockBean;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.repository.AddressRepository;
import eus.solaris.solaris.service.impl.AddressServiceImpl;

public class addressServiceImplTest {

    @InjectMocks
    AddressServiceImpl addressServiceImpl;

    @MockBean
    AddressRepository addressRepository;

    // @Test
    // public void testSave() {
    //     Address address = new Address();
    //     setAddress(address);
    //     when(addressRepository.save(address)).thenReturn(address);
    // }

    private void setAddress(Address address) {
        Country country = new Country();
        country.setId(1L);
        country.setCode("Spain");
        country.setI18n("i18n");

        address.setId(1L);
        address.setCity("Vitoria");
        address.setStreet("Rua do Sol");
        address.setNumber("1");
        address.setPostcode("01008");
        address.setCountry(country);
        address.setEnabled(true);
    }


    
}


