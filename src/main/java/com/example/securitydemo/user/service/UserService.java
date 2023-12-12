package com.example.securitydemo.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.securitydemo.user.model.User;
import com.example.securitydemo.user.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User save(User user){
        if (isUsernamePresent(user.getUsername())) {
            return null;
        }

        return repository.save(user);
    }

    public Optional<User> findUserByName(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }

    private boolean isUsernamePresent(String username) {
        return this.findUserByName(username).isPresent();
    }
}
