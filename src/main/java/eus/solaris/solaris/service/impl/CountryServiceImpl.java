package eus.solaris.solaris.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Country;
import eus.solaris.solaris.repository.CountryRepository;
import eus.solaris.solaris.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        List<Country> countries = countryRepository.findAll();
        countries.sort(Comparator.comparing(Country::getI18n));
        return countries;
    }

    @Override
    public Country findById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

}
