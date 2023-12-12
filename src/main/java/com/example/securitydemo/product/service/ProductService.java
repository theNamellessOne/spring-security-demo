package com.example.securitydemo.product.service;

import lombok.RequiredArgsConstructor;
import com.example.securitydemo.product.model.Product;
import org.springframework.stereotype.Service;
import com.example.securitydemo.product.repo.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findAllSortedByPrice() {
        return productRepository.findAll().stream()
                .sorted((x, y) -> {
                    if (x.getPrice().equals(y.getPrice())) return 0;

                    return x.getPrice() > y.getPrice() ? 1 : -1;
                })
                .toList();
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }
}
