package com.example.Shopping_Website.Service;

import com.example.Shopping_Website.Exception.CartNotFoundException;
import com.example.Shopping_Website.Exception.OrderNotFoundException;
import com.example.Shopping_Website.Models.Cart;
import com.example.Shopping_Website.Models.CartItem;
import com.example.Shopping_Website.Models.Order;
import com.example.Shopping_Website.Repositories.CartRepository;
import com.example.Shopping_Website.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;

    public Order createNewOrder(Order order,Integer userId){
        Optional<Cart> OptionalCart = cartRepository.findByUserId(userId);
        if(OptionalCart.isPresent()){
            Cart cart = OptionalCart.get();
            List<CartItem> cartItems = cart.getCartItems();
            double totalPrice = 0;
            for(CartItem item : cartItems){
                totalPrice += item.getProduct().getPrice() * item.getAmount();
            }
            order.setFullPrice(totalPrice);
            order.setArrivingDate(LocalDateTime.now().plusDays(2));
            order.setOrderDate(LocalDateTime.now());
            order.setCart(cart);
            order.setUserId(userId);
            return orderRepository.save(order);
        }else{
            throw new CartNotFoundException("Cart with id: "+ userId+ " not found");
        }
    }
    public Order viewOrderById(Integer id){
        Optional<Order> OptionalOrder = orderRepository.findById(id);
        if(OptionalOrder.isPresent()){
            Order order = OptionalOrder.get();
            return order;
        }else{
            throw new OrderNotFoundException("Order with id: "+ id+ " not found");
        }
    }
    public void cancelOrderById(Integer id){
        orderRepository.deleteById(id);
    }

}
