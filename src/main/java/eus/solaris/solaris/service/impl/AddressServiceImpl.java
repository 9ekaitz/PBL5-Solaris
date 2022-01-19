package eus.solaris.solaris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.repository.AddressRepository;
import eus.solaris.solaris.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.getById(id);
    }

    @Override
    public void delete(Address address) {
        addressRepository.delete(address);
    }

    @Override
    public Address disable(Address address) {
        address.setDefaultAddress(false);
        address.setEnabled(false);
        return addressRepository.save(address);        
    }


}
