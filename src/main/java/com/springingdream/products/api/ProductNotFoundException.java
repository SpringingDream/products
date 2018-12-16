package com.springingdream.products.api;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Integer id) {
        super("No such product: " + id);
    }
}
