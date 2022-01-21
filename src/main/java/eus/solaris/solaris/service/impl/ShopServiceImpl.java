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
        Product product = productRepository.getById(productId);

        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);

        cartProductRepository.save(cartProduct);
        user.getShoppingCart().add(cartProduct);
        return userService.save(user);
    }

    @Override
    public User cartRemoveProduct(User user, Long productId) {
        List<CartProduct> products = user.getShoppingCart();
        for (CartProduct p : products) {
            if (p.getProduct().getId() == productId) {
                products.remove(p);
                cartProductRepository.delete(p);
                break;
            }
        }
        return userService.save(user);
    }

    @Override
    public User cartUpdateProduct(User user, Long productId, Integer quantity) {
        if (quantity <= 0)
            return cartRemoveProduct(user, productId);

        List<CartProduct> products = user.getShoppingCart();
        for (CartProduct p : products) {
            if (p.getProduct().getId() == productId) {
                p.setQuantity(quantity);
                cartProductRepository.save(p);
                break;
            }
        }
        return userService.save(user);
    }

    @Override
    public User emptyCart(User user) {
        user.setShoppingCart(new ArrayList<CartProduct>());
        return userService.save(user);
    }

}
