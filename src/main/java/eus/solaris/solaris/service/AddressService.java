package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.Address;

public interface AddressService {
    public boolean save(Address address);
    public Address findById(Long id);
    
}
