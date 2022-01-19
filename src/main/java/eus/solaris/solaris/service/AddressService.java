package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.Address;

public interface AddressService {
    public Address save(Address address);
    public Address findById(Long id);
    public void delete(Address address);
    public Address disable(Address address);
}
