package eus.solaris.solaris.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}