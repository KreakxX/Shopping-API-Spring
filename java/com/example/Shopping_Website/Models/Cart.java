package com.example.Shopping_Website.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @GeneratedValue
    @Id
    private Integer id;

    private Integer userId;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;


}
