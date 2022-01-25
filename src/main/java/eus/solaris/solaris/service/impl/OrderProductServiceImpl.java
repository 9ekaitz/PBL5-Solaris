package eus.solaris.solaris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.OrderProduct;
import eus.solaris.solaris.repository.OrderProductRepository;
import eus.solaris.solaris.service.OrderProductService;

@Service
public class OrderProductServiceImpl implements OrderProductService {

  @Autowired
  OrderProductRepository orderProductRepository;

  @Override
  public OrderProduct save(OrderProduct orderProduct) {
    return orderProductRepository.save(orderProduct);
  }
  
}
