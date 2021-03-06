package eus.solaris.solaris.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eus.solaris.solaris.domain.Address;
import eus.solaris.solaris.domain.PaymentMethod;
import eus.solaris.solaris.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsernameIgnoreCase(String username);
    
    @Query("SELECT a FROM Address a WHERE a.user.id = ?1 AND a.enabled = true")
    public List<Address> findAddressByUserId(Long id);

    @Query("SELECT p FROM PaymentMethod p WHERE p.user.id = ?1 AND p.enabled = true")
    public List<PaymentMethod> findPaymentMethodByUserId(Long id);

    
}
