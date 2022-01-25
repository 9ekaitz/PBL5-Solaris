package eus.solaris.solaris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.Order;
import eus.solaris.solaris.domain.OrderProduct;
import eus.solaris.solaris.domain.OrderProductKey;
import eus.solaris.solaris.domain.Product;
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

  @Override
  public OrderProduct create(Order order, Product product, int amount) {
    OrderProduct orderProduct = new OrderProduct();
		OrderProductKey orderProductKey =  new OrderProductKey();

		orderProductKey.setOrderId(order.getId());
		orderProductKey.setProductId(product.getId());
		orderProduct.setId(orderProductKey);
		orderProduct.setProduct(product);
		orderProduct.setOrder(order);
		orderProduct.setAmount(amount);
		orderProduct.setPrice(product.getPrice());

    return orderProductRepository.save(orderProduct);
  }
  
}
