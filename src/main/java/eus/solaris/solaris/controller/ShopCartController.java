package eus.solaris.solaris.controller;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eus.solaris.solaris.domain.CartProduct;
import eus.solaris.solaris.domain.ProductDescription;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.dto.ProductDto;
import eus.solaris.solaris.dto.ShopCartManipulateDto;
import eus.solaris.solaris.dto.ShopCartOutputDto;
import eus.solaris.solaris.exception.UserNotFoundException;
import eus.solaris.solaris.repository.CartProductRepository;
import eus.solaris.solaris.repository.ProductRepository;
import eus.solaris.solaris.service.ShopService;
import eus.solaris.solaris.service.UserService;

@RestController
@RequestMapping("/shop-cart")
public class ShopCartController {

    @Autowired
    ShopService shopService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public ShopCartOutputDto get() {
        User user = getCurrentUser();
        if (user.getShoppingCart() == null) {
            user.setShoppingCart(new ArrayList<>());
            userService.save(user);
        }
        return cartToOutputDto(user);
    }

    @PostMapping("/add")
    public ShopCartOutputDto addProduct(@Validated @RequestBody ShopCartManipulateDto shopCartManipulateDto) {
        User user = getCurrentUser();
        shopService.cartAddProduct(user, shopCartManipulateDto.getProductId(), shopCartManipulateDto.getQuantity());
        return cartToOutputDto(user);
    }

    @DeleteMapping("/remove")
    public ShopCartOutputDto removeProduct(@Validated @RequestBody ShopCartManipulateDto shopCartManipulateDto) {
        User user = getCurrentUser();
        shopService.cartRemoveProduct(user, shopCartManipulateDto.getProductId());
        return cartToOutputDto(user);
    }

    @PutMapping("/update")
    public ShopCartOutputDto updateProduct(@Validated @RequestBody ShopCartManipulateDto shopCartManipulateDto) {
        User user = getCurrentUser();
        shopService.cartUpdateProduct(user, shopCartManipulateDto.getProductId(), shopCartManipulateDto.getQuantity());
        return cartToOutputDto(user);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new UserNotFoundException();
        }

        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        return user;
    }

    private ShopCartOutputDto cartToOutputDto(User user) {
        ShopCartOutputDto output = new ShopCartOutputDto();
        output.setProducts(user.getShoppingCart().stream().map(this::cartProductToDto).collect(Collectors.toList()));
        output.setTotalPrice(output.getProducts().stream().mapToDouble(ProductDto::getTotalPrice).sum());
        return output;
    }
    
    private ProductDto cartProductToDto(CartProduct cartProduct) {
        ProductDto dto = modelMapper.map(cartProduct, ProductDto.class);
        modelMapper.map(cartProduct.getProduct(), dto);
        dto.setTotalPrice(cartProduct.getQuantity() * cartProduct.getProduct().getPrice());

        Locale locale = LocaleContextHolder.getLocale();
        ProductDescription pd = cartProduct.getProduct().getDescriptions().stream().filter(p -> p.getLanguage().getCode().equals(locale.getLanguage())).findFirst().orElse(null);
        dto.setName(pd.getName());
        
        return dto;
    }
}
