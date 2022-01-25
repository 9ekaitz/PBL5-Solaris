package eus.solaris.solaris.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.CartProduct;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.CartProductRepository;
import eus.solaris.solaris.repository.ProductRepository;
import eus.solaris.solaris.service.ShopService;
import eus.solaris.solaris.service.UserService;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    UserService userService;

    @Override
    public User cartAddProduct(User user, Long productId, Integer quantity) {
        List<CartProduct> products = user.getShoppingCart();
        CartProduct cp = findInCart(products, productId);
        if (cp != null)
            return cartUpdateProduct(user, productId, quantity);

        Product product = productRepository.getById(productId);
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);
        cartProduct.setUser(user);

        cartProductRepository.save(cartProduct);
        user.getShoppingCart().add(cartProduct);
        return userService.save(user);
    }

    @Override
    public User cartRemoveProduct(User user, Long productId) {
        List<CartProduct> products = user.getShoppingCart();
        CartProduct cp = findInCart(products, productId);
        products.remove(cp);
        cartProductRepository.delete(cp);
        return userService.save(user);
    }

    @Override
    public User cartUpdateProduct(User user, Long productId, Integer quantity) {
        if (quantity <= 0)
            return cartRemoveProduct(user, productId);

        List<CartProduct> products = user.getShoppingCart();
        CartProduct cp = findInCart(products, productId);
        cp.setQuantity(quantity);
        cartProductRepository.save(cp);
        return userService.save(user);
    }

    @Override
    public User emptyCart(User user) {
        user.setShoppingCart(new ArrayList<CartProduct>());
        return userService.save(user);
    }

    private CartProduct findInCart(List<CartProduct> products, Long productId) {
        for (CartProduct cp : products) {
            if (cp.getProduct().getId().equals(productId)) {
                return cp;
            }
        }
        return null;
    }

}
