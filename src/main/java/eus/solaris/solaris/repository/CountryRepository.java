package eus.solaris.solaris.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("select c from Country c order by c.i18n")
    List<Country> findAll(String name);

    
}
