package com.example.securitydemo.token.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.securitydemo.token.model.Token;
import com.example.securitydemo.token.repo.TokenRepository;
import com.example.securitydemo.user.model.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository repository;

    public void record(Token token) {
        repository.save(token);
    }
    public void update(Token token) {
        repository.save(token);
    }

    public List<Token> getUserValidTokens(User user) {
        return repository.findTokensByUser(user).stream()
                .filter(token -> !token.isRevoked())
                .toList();
    }

    public Optional<Token> getByJwt(String jwt) {
        return repository.findByJwt(jwt);
    }

    public void recordAll(List<Token> tokens) {
        repository.saveAll(tokens);
    }
}
