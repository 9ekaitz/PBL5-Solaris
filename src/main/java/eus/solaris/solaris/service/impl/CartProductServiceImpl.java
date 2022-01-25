package eus.solaris.solaris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.CartProduct;
import eus.solaris.solaris.repository.CartProductRepository;
import eus.solaris.solaris.service.CartProductService;

@Service
public class CartProductServiceImpl implements CartProductService {

  @Autowired
  CartProductRepository cartProductRepository;

  @Override
  public CartProduct findById(Long id) {
    return cartProductRepository.findById(id).orElse(null);
  }

  @Override
  public void delete(CartProduct cartProduct) {
    cartProductRepository.delete(cartProduct);
  }
  
}
