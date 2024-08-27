package com.example.Shopping_Website.Controller;

import com.example.Shopping_Website.Models.Product;
import com.example.Shopping_Website.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")

public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/new")
    public Product createNewProduct(@RequestBody Product product){
        return service.createNewProduct(product);
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Integer id){
        return service.findProductById(id);
    }

    @GetMapping("/All/{id}")
    public List<Product> findAllProductsById(@PathVariable Integer id){
        return service.findAllProductsById(id);
    }

    @GetMapping("/{name}")
    public Product findProductByName(@PathVariable String name){
        return service.findProductByName(name);
    }

    @GetMapping("/All/{name}")
    public List<Product> findAllProductsByName(@PathVariable String name){
        return service.findAllProductsByName(name);
    }

    @PutMapping("/new/Price/id/{id}/new-price/{newprice}")
    public Product updatePriceOfProduct(@PathVariable Integer id, @PathVariable int newprice){
        return service.updatePriceOfProduct(id,newprice);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Integer id){
        service.deleteProductById(id);
    }

    @DeleteMapping("/delete/All")
    public void deleteAll(){
        service.deleteAll();
    }
}
