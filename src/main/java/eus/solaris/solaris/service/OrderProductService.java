package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.Order;
import eus.solaris.solaris.domain.OrderProduct;
import eus.solaris.solaris.domain.Product;

public interface OrderProductService {
  public OrderProduct save(OrderProduct orderProduct);
  public OrderProduct create(Order order, Product product, int amount);
}
