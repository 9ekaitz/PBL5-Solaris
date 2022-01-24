package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.ProductDescription;

@Repository
public interface ProductDescriptionRepository extends JpaRepository<ProductDescription, Long> {

    
}
