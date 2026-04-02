package com.stockFlix.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    
    private final SecretKey SECRET = Keys.hmacShaKeyFor(
        Decoders.BASE64.decode(System.getenv("JWT_SECRET"))) ;
    private final long EXPIRACAO = 1000 * 60 * 60 * 8; //Calculo para 8 horas de expiração

    public String gerarToken(String email, String tipo) {
        return Jwts.builder()
            .subject(email)
            .claim("role", tipo)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRACAO))
            .signWith(SECRET)
            .compact();
    }

    public String extrairEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validarToken(String token, String email) {
        String emailDoToken = extrairEmail(token);
        return emailDoToken.equals(email) && !tokenExpirado(token);
    }

    public boolean tokenExpirado(String token) { 
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
