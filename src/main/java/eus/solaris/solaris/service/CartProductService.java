package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.CartProduct;

public interface CartProductService {
  public CartProduct findById(Long id);
  public void delete(CartProduct cartProduct);
}
