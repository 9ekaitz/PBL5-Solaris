package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.CartProduct;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    
}
