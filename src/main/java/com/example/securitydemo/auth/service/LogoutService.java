package com.example.securitydemo.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import com.example.securitydemo.token.model.Token;
import com.example.securitydemo.token.service.TokenService;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenService tokenService;
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return;
        }

        final String jwt = header.substring(7);
        Token activeToken = tokenService.getByJwt(jwt).orElse(null);
        if (activeToken != null) {
            activeToken.setRevoked(true);
            tokenService.update(activeToken);
        }
    }
}
