package com.example.Shopping_Website.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @GeneratedValue
    @Id
    private  Integer id;

    @NotNull
    private Integer price;

    @NotNull
    private String name;

    @NotNull
    private String description;
}
