package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.User;

public interface ShopService {

    public User cartAddProduct(User user, Long productId, Integer quantity);
    public User cartRemoveProduct(User user, Long productId);
    public User cartUpdateProduct(User user, Long productId, Integer quantity);
    public User emptyCart(User user);

}
