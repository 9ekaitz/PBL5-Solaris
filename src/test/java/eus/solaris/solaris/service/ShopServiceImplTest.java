package eus.solaris.solaris.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import eus.solaris.solaris.domain.CartProduct;
import eus.solaris.solaris.domain.Product;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.repository.CartProductRepository;
import eus.solaris.solaris.repository.ProductRepository;
import eus.solaris.solaris.service.impl.ShopServiceImpl;
import eus.solaris.solaris.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class ShopServiceImplTest {

    @InjectMocks
    private ShopServiceImpl shopServiceImpl;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartProductRepository cartProductRepository;

    @Mock
    private UserServiceImpl userService;

    @Test
    void cartAddProductTest(){
        User basicUser = new User();
        User basicUserChanged = new User();

        Product product = new Product(1L, 200D, null, null, null, 1);

        List<CartProduct> cartProducts = Stream
            .of(new CartProduct(1L, product, 1, basicUser, 1)).collect(Collectors.toList());

        basicUser.setShoppingCart(new ArrayList<>());
        basicUserChanged.setShoppingCart(cartProducts);

        when(userService.save(basicUser)).thenReturn(basicUserChanged);
        when(productRepository.getById(1L)).thenReturn(product);

        assertEquals(basicUserChanged, shopServiceImpl.cartAddProduct(basicUser, 1L, 1));
    }

    @Test
    void cartRemoveProductTest() {
        User basicUser = new User();
        User basicUserChanged = new User();

        Product product = new Product(1L, 200D, null, null, null, 1);

        List<CartProduct> cartProducts = Stream
        .of(new CartProduct(1L, product, 1, basicUser, 1)).collect(Collectors.toList());
        basicUser.setShoppingCart(cartProducts);

        when(userService.save(basicUser)).thenReturn(basicUserChanged);

        assertEquals(basicUserChanged, shopServiceImpl.cartRemoveProduct(basicUser, 1L));
    }

    @Test
    void cartUpdateProductTest() {
        User basicUser = new User();
        User basicUserChanged = new User();

        Product product = new Product(1L, 200D, null, null, null, 1);

        List<CartProduct> cartProducts = Stream
        .of(new CartProduct(1L, product, 1, basicUser, 1)).collect(Collectors.toList());
        basicUser.setShoppingCart(cartProducts);

        List<CartProduct> cartProducts2 = Stream
        .of(new CartProduct(1L, product, 5, basicUser, 1)).collect(Collectors.toList());
        basicUser.setShoppingCart(cartProducts);

        basicUser.setShoppingCart(cartProducts);
        basicUserChanged.setShoppingCart(cartProducts2);

        when(userService.save(basicUser)).thenReturn(basicUserChanged);

        assertEquals(basicUserChanged, shopServiceImpl.cartUpdateProduct(basicUser, 1L, 5));

    }

    @Test
    void emptyCartTest() {
        User basicUser = new User();
        User basicUserChanged = new User();

        Product product = new Product(1L, 200D, null, null, null, 1);

        List<CartProduct> cartProducts = Stream
        .of(new CartProduct(1L, product, 1, basicUser, 1)).collect(Collectors.toList());
        basicUser.setShoppingCart(cartProducts);

        when(userService.save(basicUser)).thenReturn(basicUserChanged);

        assertEquals(basicUserChanged, shopServiceImpl.emptyCart(basicUser));

    }
    
}
