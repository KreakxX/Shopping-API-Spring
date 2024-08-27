package com.example.Shopping_Website.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders")
public class Order {
    @GeneratedValue
    @Id
    private Integer id;

    private Integer userId;

    @OneToOne
    private Cart cart;

    private LocalDateTime orderDate;

    private LocalDateTime arrivingDate;

    private double fullPrice;


}
