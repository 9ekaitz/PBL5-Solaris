package eus.solaris.solaris.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import eus.solaris.solaris.domain.ShopCart;
import eus.solaris.solaris.domain.User;
import eus.solaris.solaris.dto.ShopCartManipulateDto;
import eus.solaris.solaris.dto.ShopCartOutputDto;
import eus.solaris.solaris.exception.UserNotFoundException;
import eus.solaris.solaris.service.ShopService;
import eus.solaris.solaris.service.UserService;

@RestController
@RequestMapping("/shop-cart")
public class ShopCartController {

    @Autowired
    ShopService shopService;

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/")
    public ShopCartOutputDto get() {
        User user = getCurrentUser();
        ShopCart cart = shopService.getCartByUser(user);
        return cartToOutputDto(cart);
    }
    
    @PostMapping("/add")
    public ShopCartOutputDto addProduct(@Validated @RequestBody ShopCartManipulateDto shopCartManipulateDto) {
        User user = getCurrentUser();
        ShopCart cart = shopService.cartAddProduct(user, shopCartManipulateDto.getProductId(), shopCartManipulateDto.getQuantity());
        return cartToOutputDto(cart);
    }
    
    @DeleteMapping("/remove")
    public ShopCartOutputDto removeProduct(@Validated @RequestBody ShopCartManipulateDto shopCartManipulateDto) {
        User user = getCurrentUser();
        ShopCart cart = shopService.cartRemoveProduct(user, shopCartManipulateDto.getProductId());
        return cartToOutputDto(cart);
    }

    @PutMapping("/update")
    public ShopCartOutputDto updateProduct(@Validated @RequestBody ShopCartManipulateDto shopCartManipulateDto) {
        User user = getCurrentUser();
        ShopCart cart = shopService.cartUpdateProduct(user, shopCartManipulateDto.getProductId(), shopCartManipulateDto.getQuantity());
        return cartToOutputDto(cart);
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

    private ShopCartOutputDto cartToOutputDto(ShopCart cart) {
        return modelMapper.map(cart, ShopCartOutputDto.class);
    }

}
