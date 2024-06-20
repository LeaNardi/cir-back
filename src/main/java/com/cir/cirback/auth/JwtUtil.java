package com.cir.cirback.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import com.cir.cirback.entities.Role;

import java.util.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Component
public class JwtUtil {
    private String SECRET_KEY = "secret";

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(SECRET_KEY.getBytes())).compact();
    }
    
    public String generateToken(String userName, Integer role_id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role_id);
        return createToken(claims, userName);
    }
    
    private Claims extractAllClaims(String token) {
    	return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public Boolean validateToken(String token, String username) {
        final String username_token = extractUsername(token);
        return (username_token.equals(username) && !isTokenExpired(token));
    }
}
