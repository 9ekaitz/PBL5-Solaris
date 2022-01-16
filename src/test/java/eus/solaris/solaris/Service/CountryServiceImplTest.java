package eus.solaris.solaris.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.repository.CountryRepository;
import eus.solaris.solaris.service.impl.CountryServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CountryServiceImplTest {

    @InjectMocks
    private CountryServiceImpl countryServiceImpl;

    @Mock
    private CountryRepository countryRepository;

    @Test
    void findAllTest() {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1L, "COUNTRY_SPAIN", "country.spain", 1));
        countries.add(new Country(2L, "COUNTRY_FRANCE", "country.france", 2));

        when(countryRepository.findAll()).thenReturn(countries);
        assertEquals(countries, countryServiceImpl.findAll());
    }

    @Test
    void findByIdTest(){
        Country country = new Country(1L, "COUNTRY_SPAIN", "country.spain", 1);

        when(countryRepository.findById(1L)).thenReturn(java.util.Optional.of(country));
        assertEquals(country, countryServiceImpl.findById(1L));
    }
    
}
