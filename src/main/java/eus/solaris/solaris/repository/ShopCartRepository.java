package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.ShopCart;
import eus.solaris.solaris.domain.User;

@Repository
public interface ShopCartRepository extends JpaRepository<ShopCart, Long> {

    public ShopCart findByUser(User user);
    
}
