package com.example.Shopping_Website.Repositories;

import com.example.Shopping_Website.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Optional<List<Product>> findAllById(Integer id);
    Optional<Product> findProductByName(String name);
    Optional<List<Product>> findAllByName(String name);
    void deleteAllById(Integer id);
}
