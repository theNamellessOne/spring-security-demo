package com.example.securitydemo.product.controller;

import lombok.RequiredArgsConstructor;
import com.example.securitydemo.product.model.Product;
import org.springframework.web.bind.annotation.*;
import com.example.securitydemo.product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public Product findById(@PathVariable(name = "id") String id) {
        return productService.findById(id);
    }

    @GetMapping("/")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/sorted")
    public List<Product> findAllSortedByPrice() {
        return productService.findAllSortedByPrice();
    }


    @PostMapping("/")
    public Product save(@RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") String id) {
        productService.delete(id);
    }
}
