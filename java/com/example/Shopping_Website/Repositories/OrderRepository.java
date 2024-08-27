package com.example.Shopping_Website.Repositories;


import com.example.Shopping_Website.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
