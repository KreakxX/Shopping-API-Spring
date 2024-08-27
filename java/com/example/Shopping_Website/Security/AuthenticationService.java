package com.example.Shopping_Website.Security;

import com.example.Shopping_Website.DTO.AuthenticationRequest;
import com.example.Shopping_Website.DTO.AuthenticationResponse;
import com.example.Shopping_Website.DTO.RegisterRequest;
import com.example.Shopping_Website.Models.Roles;
import com.example.Shopping_Website.Models.User;
import com.example.Shopping_Website.Repositories.UserRepository;
import com.example.Shopping_Website.token.Token;
import com.example.Shopping_Website.token.TokenRepository;
import com.example.Shopping_Website.token.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final JwtService service;
    private final AuthenticationManager manager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request){
        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Roles.USER)
                .build();
        var savedUser =  repository.save(user);
        var JWT =  service.generateToken(user);
        saveUserToken(savedUser, JWT);
        var RefreshToken = service.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(JWT)
                .refreshToken(RefreshToken)
                .build();
    }

    public AuthenticationResponse authenticate (AuthenticationRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var JWT = service.generateToken(user);
        revokeAllUserTokes(user);
        saveUserToken(user,JWT);
        var RefreshToken = service.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(JWT)
                .refreshToken(RefreshToken)
                .build();

    }
    public void refreshToken(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response
    ) throws IOException {
        String authHeader = request.getHeader("Authorization");
        String userEmail;
        String Refreshtoken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        Refreshtoken = authHeader.substring(7);
        userEmail = service.extractUsername(Refreshtoken);
        if (userEmail != null) {
            var user = repository.findByEmail(userEmail).orElseThrow();
            if (service.isTokenValid(Refreshtoken, user)) {
                var accesToken = service.generateToken(user);
                var authenticationResponse = AuthenticationResponse.builder()
                        .refreshToken(Refreshtoken)
                        .accessToken(accesToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
            }
        }
    }

    private void saveUserToken(User user, String JWT) {
        var token = Token.builder()
                .user(user)
                .token(JWT)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }


   private void revokeAllUserTokes(User user){
        var storedTokens = tokenRepository.findAllByValidTokensByUser(user.getId());
        if(storedTokens.isEmpty()){
            return;
        }
        storedTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(storedTokens);
   }

}
