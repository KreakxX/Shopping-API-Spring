package com.example.Shopping_Website.Service;

import com.example.Shopping_Website.DTO.CartDTO;
import com.example.Shopping_Website.Exception.CartItemNotFoundException;
import com.example.Shopping_Website.Exception.CartNotFoundException;
import com.example.Shopping_Website.Exception.ProductNotFoundException;
import com.example.Shopping_Website.Exception.UserNotFoundException;
import com.example.Shopping_Website.Mapper.CartMapper;
import com.example.Shopping_Website.Models.Cart;
import com.example.Shopping_Website.Models.CartItem;
import com.example.Shopping_Website.Models.Product;
import com.example.Shopping_Website.Models.User;
import com.example.Shopping_Website.Repositories.CartRepository;
import com.example.Shopping_Website.Repositories.CartitemRepository;
import com.example.Shopping_Website.Repositories.ProductRepository;
import com.example.Shopping_Website.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository repository;
    @Autowired
    private CartitemRepository carItemRepository;
    @Autowired
    private ProductRepository ProductRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartMapper mapper;

    public Cart createNewCart(Integer userId){
        Cart cart = new Cart();
        cart.setId(userId);
        cart.setUserId(userId);
        return repository.save(cart);
    }

    public Cart addItemsToCart(Integer userId, Integer Product_Id, int amount){
        Optional<Cart> OptionalCart = repository.findByUserId(userId);
        if(OptionalCart.isPresent()){
            Cart cart = OptionalCart.get();
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            Optional<Product> OptionalProduct = ProductRepository.findById(Product_Id);
            if(OptionalProduct.isPresent()){
                Product product = OptionalProduct.get();
                cartItem.setProduct(product);
                cartItem.setAmount(amount);
                carItemRepository.save(cartItem);
                cart.getCartItems().add(cartItem);
                Optional<User> OptionalUser = userRepository.findById(userId);
                if(OptionalUser.isPresent()){
                    User user = OptionalUser.get();
                    user.setCart(cart);
                    userRepository.save(user);
                }
                return repository.save(cart);
            }else{
                throw new ProductNotFoundException("Product with id: "+ Product_Id+ " not found");
            }
        }else{
            throw new CartNotFoundException("Cart with id: "+ userId + " not found");
        }
    }

    public Cart updateAmountOfCartItem(Integer User_ID, Integer CartItem_Id, int newAmount){
        Optional<Cart> OptionalCart = repository.findByUserId(User_ID);
        if(OptionalCart.isPresent()){
            Cart cart = OptionalCart.get();
            List<CartItem> cartItems = cart.getCartItems();
            boolean productfound = false;
            for(CartItem cartItem: cartItems){
                if(cartItem.getId().equals(CartItem_Id)){
                    cartItem.setAmount(newAmount);
                    productfound = true;
                    break;
                }
            }
            if(productfound){
                repository.save(cart);
                return cart;
            }else{
                throw new RuntimeException("CartItem with id: "+ CartItem_Id + " not found" );
            }
        }else{
            throw new CartItemNotFoundException("Cart with id: "+ User_ID+" not found");
        }
    }


    public Cart findCartByIdNoDto(Integer cart_id){
        Optional<Cart> OptionalCart = repository.findById(cart_id);
        if(OptionalCart.isPresent()){
            Cart cart = OptionalCart.get();
            return cart;
        }else{
            throw new CartNotFoundException("Cart with id: "+ cart_id+" not found");
        }
    }
    public CartDTO findCartById(Integer cart_id){
        Optional<Cart> OptionalCart = repository.findById(cart_id);
        if(OptionalCart.isPresent()){
            Cart cart = OptionalCart.get();
            CartDTO dto = mapper.toCartDto(cart);
            return dto;
        }else{
            throw new CartNotFoundException("Cart with id: "+ cart_id+" not found");
        }
    }

    @Secured("ROLE_ADMIN")
    public void deleteCartById(Integer cart_id){
        repository.deleteById(cart_id);
    }

    @Secured("ROLE_ADMIN")
    public void deleteAllCarts(){
        repository.deleteAll();
    }
}
