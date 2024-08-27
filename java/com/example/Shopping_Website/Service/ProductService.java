package com.example.Shopping_Website.Service;

import com.example.Shopping_Website.Exception.ProductNotFoundException;
import com.example.Shopping_Website.Models.Product;
import com.example.Shopping_Website.Repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product createNewProduct(Product product){
        return repository.save(product);
    }

    public Product findProductById(Integer id){
        Optional<Product> OptionalProduct = repository.findById(id);
        if(OptionalProduct.isPresent()){
            Product product = OptionalProduct.get();
            return product;
        }
        else {
            throw new ProductNotFoundException("Product with id: "+ id+ " not found");
        }
    }

    public List<Product> findAllProductsById(Integer id){
        Optional<List<Product>> OptionalProducts = repository.findAllById(id);
        if(OptionalProducts.isPresent()){
            List<Product> products = OptionalProducts.get();
            return products;
        }else{
            throw new ProductNotFoundException("Products with id: "+ id+ " not found");
        }
    }

    public Product findProductByName(String name){
        Optional<Product> OptionalProduct = repository.findProductByName(name);
        if(OptionalProduct.isPresent()){
            Product product = OptionalProduct.get();
            return product;
        }else{
            throw  new ProductNotFoundException("Product with name: "+ name +" not found");
        }
    }

    public List<Product> findAllProductsByName(String name){
        Optional<List<Product>> OptionalProducts = repository.findAllByName(name);
        if(OptionalProducts.isPresent()){
            List<Product> products = OptionalProducts.get();
            return products;
        }else{
            throw new ProductNotFoundException("Product with name: "+ name +" not found");
        }
    }

    public Product updatePriceOfProduct(Integer id, int newPrice){
        Optional<Product> OptionalProduct = repository.findById(id);
        if(OptionalProduct.isPresent()){
            Product newProduct = OptionalProduct.get();
            newProduct.setPrice(newPrice);
            return repository.save(newProduct);
        }else{
            throw new ProductNotFoundException("Product with id: "+ id+ " not found");
        }
    }
    @Secured("ROLE_ADMIN")
    public void deleteProductById(Integer id){
        repository.deleteById(id);
    }

    public void deleteAllProductsById(Integer id){
        repository.deleteAllById(id);
    }

    @Secured("ROLE_ADMIN")
    public void deleteAll(){
        repository.deleteAll();
    }






}
