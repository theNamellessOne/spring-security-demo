package com.example.securitydemo.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    @Id
    private String id;

    private String name;
    private String description;
    private Double price;
}
