package eus.solaris.solaris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.repository.AddressRepository;

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


}
