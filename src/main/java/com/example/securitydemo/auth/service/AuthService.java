package com.example.securitydemo.auth.service;

import com.example.securitydemo.auth.dto.AuthRequest;
import com.example.securitydemo.auth.dto.AuthResponse;
import com.example.securitydemo.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.securitydemo.token.TokenType;
import com.example.securitydemo.token.model.Token;
import com.example.securitydemo.token.service.TokenService;
import com.example.securitydemo.user.Role;
import com.example.securitydemo.user.model.User;
import com.example.securitydemo.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(AuthRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User userRecorded = userService.save(user);
        if (userRecorded == null) {
            return new AuthResponse("Persistence failed");
        }

        String jwt = jwtService.generateJwt(user);
        Token token = Token.builder()
                .user(userRecorded)
                .jwt(jwt)
                .type(TokenType.BEARER)
                .revoked(false)
                .build();
        tokenService.record(token);
        return new AuthResponse(jwt);
    }
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        //   var com.example.securitydemo.user = userDetailsService.loadUserByUsername(request.getUsername());
        User userExtracted = userService.findUserByName(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateJwt(userExtracted);
        Token token = Token.builder()
                .user(userExtracted)
                .jwt(jwt)
                .type(TokenType.BEARER)
                .revoked(false)
                .build();
        revokeAllTokensByUser(userExtracted);
        tokenService.record(token);
        return new AuthResponse(jwt);
    }

    private void revokeAllTokensByUser(User user) {
        List<Token> allValidTokensByUser = tokenService.getUserValidTokens(user);
        if (allValidTokensByUser.isEmpty()) {
            return;
        }
        allValidTokensByUser.forEach(token -> token.setRevoked(true));
        tokenService.recordAll(allValidTokensByUser);
    }
}
