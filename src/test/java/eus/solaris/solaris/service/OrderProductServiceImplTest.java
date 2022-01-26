package eus.solaris.solaris.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import eus.solaris.solaris.domain.Order;
import eus.solaris.solaris.domain.OrderProduct;
import eus.solaris.solaris.domain.OrderProductKey;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.repository.OrderProductRepository;
import eus.solaris.solaris.service.impl.OrderProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderProductServiceImplTest {

  @InjectMocks
  private OrderProductServiceImpl orderProductServiceImpl;

  @Mock
  private OrderProductRepository orderProductRepository;

  @Test
  void saveTest() {
    OrderProduct orderProduct = new OrderProduct(new OrderProductKey(), new Order(), new Product(), 1, 1.0, 0);

    when(orderProductRepository.save(orderProduct)).thenReturn(orderProduct);
    assertEquals(orderProduct, orderProductServiceImpl.save(orderProduct));
  }

  @Test
  void createTest() {
    Double price = 1.0;

    Product p = new Product();
    p.setId(1L);
    p.setPrice(price);

    Order o = new Order();
    o.setId(1L);

    OrderProductKey key = new OrderProductKey();
    key.setOrderId(o.getId());
    key.setProductId(o.getId());

    int amount = 1;

    OrderProduct orderProduct = new OrderProduct(key, o, p, amount, p.getPrice(), 0);

    when(orderProductRepository.save(orderProduct)).thenReturn(orderProduct);
    assertEquals(orderProduct, orderProductServiceImpl.create(o, p, amount));
  }
}
