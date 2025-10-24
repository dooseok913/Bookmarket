package com.springboot.exception;

@SuppressWarnings("serial")
public class CartException extends RuntimeException {
    private static final long serialVersionUID = -5192041563L;
    private String cartId;

    public CartException (String cartId) {
        this.cartId = cartId;
    }

}
