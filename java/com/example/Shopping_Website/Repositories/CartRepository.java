package com.example.Shopping_Website.Repositories;

import com.example.Shopping_Website.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Optional<Cart> findByUserId(Integer UserId);
}
