package com.example.Shopping_Website.Exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message){
       super(message);
    }
}
