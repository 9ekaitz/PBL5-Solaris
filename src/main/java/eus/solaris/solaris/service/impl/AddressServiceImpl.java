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
    public boolean save(Address address) {
        return addressRepository.save(address) != null;
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
    public void disable(Address address) {
        address.setEnabled(false);
        addressRepository.save(address);        
    }


}
