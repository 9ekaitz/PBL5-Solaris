package eus.solaris.solaris.repository.filters;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    
}