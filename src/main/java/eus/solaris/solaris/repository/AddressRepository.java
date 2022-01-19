package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    
}
