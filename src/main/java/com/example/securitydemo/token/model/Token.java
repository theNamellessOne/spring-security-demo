package com.example.securitydemo.token.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.example.securitydemo.token.TokenType;
import com.example.securitydemo.user.model.User;

@Document

@Data
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class Token {
    @Id
    private String id;
    private User user;
    private String jwt;
    private TokenType type;
    private boolean revoked;
}