package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.solaris.solaris.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
  
}
