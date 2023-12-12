package com.example.securitydemo.token.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.securitydemo.token.model.Token;
import com.example.securitydemo.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {
    List<Token> findTokensByUser(User user);
    Optional<Token> findByJwt(String jwt);
}
