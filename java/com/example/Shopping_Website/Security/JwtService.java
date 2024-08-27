package com.example.Shopping_Website.Security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final String SecretKey = "F705CEC544A68D87EE24773CB0E1930C6A7C1031195F3495F2CCA033D9DB0561";
    private final long JWTExpiration = 86400000;
    private final long RefreshExpiration = 604800000;

    public Key getSigningKey(){
        return Keys.hmacShaKeyFor(SecretKey.getBytes());
    }
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String buildToken(Map<String,Object> extracClaims,UserDetails userDetails,long expiration){
        return Jwts.builder()
                .setClaims(extracClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(Map<String,Object> extraClaims,UserDetails userDetails){
        extraClaims.put("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return buildToken(extraClaims,userDetails,JWTExpiration);
    }
    public String generateToken(UserDetails userDetails){
        return  generateToken(new HashMap<>(), userDetails);
    }
    public String generateRefreshToken(UserDetails userDetails){
        return buildToken(new HashMap<>(),userDetails,RefreshExpiration);
    }
    public boolean isTokenValid(String token, UserDetails details){
        String username = extractUsername(token);
        return (username.equals(details.getUsername())&& !isTokenExpired(token));
    }
    public boolean isTokenExpired(String token){
       return (extractExpiration(token).before(new Date()));
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
}

