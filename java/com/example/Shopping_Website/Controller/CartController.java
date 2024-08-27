package com.example.Shopping_Website.Controller;

import com.example.Shopping_Website.DTO.CartDTO;
import com.example.Shopping_Website.Models.Cart;
import com.example.Shopping_Website.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")

public class CartController {
    @Autowired
    private CartService service;

    @PostMapping("/new/{userid}")
    public Cart createNewCart(@PathVariable Integer userid) {
        return service.createNewCart(userid);
    }

    @PutMapping("{userid}/new/items/product/{productid}/amount/{amount}")
    public Cart addCartItemsToCart(@PathVariable Integer userid, @PathVariable Integer productid, @PathVariable int amount){
        return service.addItemsToCart(userid,productid,amount);
    }

    @PutMapping("/{userid}/cartitem/{cartitemid}/new-amount/{newamount}")
    public Cart updateAmountOfCartItem(
            @PathVariable Integer userid, @PathVariable Integer cartitemid, @PathVariable int newamount
    ){
        return service.updateAmountOfCartItem(userid,cartitemid,newamount);
    }
    @GetMapping("/{id}/NoDto")
    public Cart findCartByIdNoDto(@PathVariable Integer id){
        return service.findCartByIdNoDto(id);
    }
    @GetMapping("/{id}")
    public CartDTO findCartById(@PathVariable Integer id){
        return service.findCartById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteCartById(@PathVariable Integer id){
        service.deleteCartById(id);
    }
    @DeleteMapping("/delete/All")
    public void deleteAllCarts(){
        service.deleteAllCarts();
    }
}
