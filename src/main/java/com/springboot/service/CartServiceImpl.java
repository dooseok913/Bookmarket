package com.springboot.service;

import com.springboot.domain.Cart;
import com.springboot.exception.CartException;
import com.springboot.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements  CartService {
    @Autowired
    private CartRepository cartRepository;
//    @Autowired
//    private CartException cartException;
    public Cart create(Cart cart) {
        return cartRepository.create(cart);
    }
    public Cart read(String cartId) {
        return cartRepository.read(cartId);
    }

    public Cart validateCart(String cartId) {

        Cart cart = cartRepository.read(cartId);
        if(cart == null || cart.getCartItems().size() == 0) {
            throw new CartException(cartId);
        }
        return  cart;
    }
    public void update(String cartId, Cart cart) {
        cartRepository.update(cartId, cart);

    }

    public  void delete(String cartId) {
        cartRepository.delete(cartId);
    }


}
