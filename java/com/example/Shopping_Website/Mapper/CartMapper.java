package com.example.Shopping_Website.Mapper;

import com.example.Shopping_Website.DTO.CartDTO;
import com.example.Shopping_Website.Models.Cart;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public class CartMapper {
    public Cart toCart(CartDTO dto){
        Cart cart = new Cart();
        cart.setUserId(dto.userId);
        return cart;
    }
    public CartDTO toCartDto(Cart cart){
        CartDTO dto = new CartDTO();
        dto.setUserId(cart.getUserId());
        return dto;
    }
}
