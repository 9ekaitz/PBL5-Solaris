package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    
}
