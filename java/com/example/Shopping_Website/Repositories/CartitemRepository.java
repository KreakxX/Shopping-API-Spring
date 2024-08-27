package com.example.Shopping_Website.Repositories;

import com.example.Shopping_Website.Models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartitemRepository extends JpaRepository<CartItem,Integer> {
    void deleteAllById(Integer id);
}
