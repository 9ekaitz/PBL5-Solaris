package eus.solaris.solaris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Order;
import eus.solaris.solaris.repository.OrderRepository;
import eus.solaris.solaris.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

@Autowired
OrderRepository orderRepository;

  @Override
  public Order save(Order order) {
    return orderRepository.save(order);
  }
  
}
