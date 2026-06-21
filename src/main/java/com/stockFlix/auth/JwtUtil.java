package com.stockFlix.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

/**
 * Classe utilitária responsável por operações com JWT (JSON Web Token).
 * 
 * <p>Gerencia a criação, validação e extração de informações dos tokens
 * utilizados para autenticação na aplicação.</p>
 * 
 * <p>Os tokens gerados são assinados digitalmente utilizando uma chave secreta,
 * garantindo integridade e segurança.</p>
 */
@Component
@RequiredArgsConstructor
public class JwtUtil {
    
    private final SecretKey SECRET = Keys.hmacShaKeyFor(
        Decoders.BASE64.decode(System.getProperty("JWT_SECRET"))) ;
    private final long EXPIRACAO = 1000 * 60 * 60 * 8; //Calculo para 8 horas de expiração

    /**
     * Gera um novo token JWT.
     * 
     * @param email identificador do usuário
     * @param tipo role/perfil do usuário (ex: ADMIN, USER)
     * @return token JWT gerado
     */
    public String gerarToken(String email, String tipo) {
        return Jwts.builder()
            .subject(email)
            .claim("role", tipo)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRACAO))
            .signWith(SECRET)
            .compact();
    }

    /**
     * Extrai o email (subject) do token.
     * 
     * @param token token JWT
     * @return email do usuário
     */
    public String extrairEmail(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extrai a role do usuário presente no token.
     * 
     * @param token token JWT
     * @return tipo/role do usuário
     */
    public String extrairTipo(String token) {
        return getClaims(token).get("role", String.class);
    }

    /**
     * Valida o token comparando o email e verificando se não está expirado.
     * 
     * @param token token JWT
     * @param email email esperado
     * @return true se válido, false caso contrário
     */
    public boolean validarToken(String token, String email) {
        String emailDoToken = extrairEmail(token);
        return emailDoToken.equals(email) && !tokenExpirado(token);
    }

    /**
     * Verifica se o token está expirado.
     * 
     * @param token token JWT
     * @return true se expirado
     */
    private boolean tokenExpirado(String token) { 
        return getClaims(token).getExpiration().before(new Date());
    }


    /**
     * Obtém as claims (dados) do token.
     * 
     * <p>As claims incluem informações como subject, role e expiração.</p>
     * 
     * @param token token JWT
     * @return objeto Claims contendo os dados do token
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
