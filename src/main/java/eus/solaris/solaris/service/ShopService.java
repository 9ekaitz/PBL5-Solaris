package eus.solaris.solaris.service;

import eus.solaris.solaris.domain.ShopCart;
import eus.solaris.solaris.domain.User;

public interface ShopService {

    public ShopCart save(ShopCart cart);
    public ShopCart cartAddProduct(User user, Long productId, Integer quantity);
    public ShopCart cartRemoveProduct(User user, Long productId);
    public ShopCart cartUpdateProduct(User user, Long productId, Integer quantity);
    public ShopCart getCartByUser(User user);
    public ShopCart emptyCart(User user);

}
