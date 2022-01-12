package eus.solaris.solaris.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eus.solaris.solaris.domain.CartProduct;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.ShopCart;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.CartProductRepository;
import eus.solaris.solaris.repository.ProductRepository;
import eus.solaris.solaris.repository.ShopCartRepository;
import eus.solaris.solaris.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopCartRepository shopCartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartProductRepository cartProductRepository;

    @Override
    public ShopCart save(ShopCart cart) {
        return shopCartRepository.save(cart);
    }

    @Override
    public ShopCart cartAddProduct(User user, Long productId, Integer quantity) {
        ShopCart cart = getCartByUser(user);
        Product product = productRepository.getById(productId);

        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        cartProduct.setQuantity(quantity);

        cartProductRepository.save(cartProduct);
        cart.getProducts().add(cartProduct);
        return save(cart);
    }

    @Override
    public ShopCart cartRemoveProduct(User user, Long productId) {
        ShopCart cart = getCartByUser(user);
        List<CartProduct> products = cart.getProducts();
        for (CartProduct p : products) {
            if (p.getProduct().getId() == productId) {
                products.remove(p);
                break;
            }
        }
        return save(cart);
    }
    
    @Override
    public ShopCart cartUpdateProduct(User user, Long productId, Integer quantity) {
        ShopCart cart = getCartByUser(user);
        List<CartProduct> products = cart.getProducts();
        for (CartProduct p : products) {
            if (p.getProduct().getId() == productId) {
                p.setQuantity(quantity);
                break;
            }
        }
        return save(cart);
    }
    
    @Override
    public ShopCart getCartByUser(User user) {
        ShopCart cart = shopCartRepository.findByUser(user);
        if(cart == null) {
            cart = new ShopCart();
            cart.setUser(user);
            cart.setProducts(new ArrayList<CartProduct>());
            save(cart);
        }
        return cart;
    }
    
    @Override
    public ShopCart emptyCart(User user) {
        ShopCart cart = getCartByUser(user);
        cart.setProducts(new ArrayList<CartProduct>());
        return save(cart);
    }

}
