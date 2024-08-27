package com.example.Shopping_Website.Security;

import com.example.Shopping_Website.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository repository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String authheader = request.getHeader("Authorization");
        String jwt;
        if(authheader == null || !authheader.startsWith("Bearer ")){
            return;
        }
        jwt = authheader.substring(7);
        var storedToken = repository.findByToken(jwt).orElse(null);
        if(storedToken!=null){
            storedToken.setRevoked(true);
            storedToken.setExpired(true);
            repository.save(storedToken);
        }
    }
}
