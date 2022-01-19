package eus.solaris.solaris.service;

import java.util.List;

import eus.solaris.solaris.domain.Country;

public interface CountryService {
    public List<Country> findAll();
    public Country findById(Long id);
    
}
