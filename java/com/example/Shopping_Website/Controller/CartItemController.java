package com.example.Shopping_Website.Controller;

import com.example.Shopping_Website.Models.CartItem;
import com.example.Shopping_Website.Service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cartitems")
public class CartItemController {
    @Autowired
    private CartItemService service;

    @PostMapping("/new")
    public CartItem createNewCartItem(@RequestBody CartItem cartItem){
        return service.createNewCartItem(cartItem);
    }

    @GetMapping("/{id}")
    public CartItem findCartItemById(@PathVariable Integer id){
        return service.findCartItemById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Integer id){
        service.deleteCartItemById(id);
    }

    @DeleteMapping("/delete/All")
    public void deleteAll(){
        service.deleteAll();
    }
}
