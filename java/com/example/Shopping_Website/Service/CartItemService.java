package com.example.Shopping_Website.Service;

import com.example.Shopping_Website.Exception.CartItemNotFoundException;
import com.example.Shopping_Website.Models.CartItem;
import com.example.Shopping_Website.Repositories.CartitemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CartItemService {
    @Autowired
    CartitemRepository repository;

    public CartItem createNewCartItem(CartItem cartItem){
        return repository.save(cartItem);
    }


    public CartItem findCartItemById(Integer id){
        Optional<CartItem> OptionalCartItem = repository.findById(id);
        if(OptionalCartItem.isPresent()){
            CartItem cartItem = OptionalCartItem.get();
            return cartItem;
    }else{
            throw new CartItemNotFoundException("CartItem with id: "+ id+ "not found");
        }
    }
    @Secured("ROLE_ADMIN")
    public void deleteCartItemById(Integer id){
        repository.deleteById(id);
    }

    public void deleteAllCartItemsById(Integer id){
        repository.deleteAllById(id);
    }

    @Secured("ROLE_ADMIN")
    public void deleteAll(){
        repository.deleteAll();
    }
}
