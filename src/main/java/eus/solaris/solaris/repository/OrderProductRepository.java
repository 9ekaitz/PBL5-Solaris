package eus.solaris.solaris.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eus.solaris.solaris.domain.OrderProduct;
import eus.solaris.solaris.domain.OrderProductKey;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductKey> {
  
}
