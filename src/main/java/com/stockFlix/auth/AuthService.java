package com.stockFlix.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.stockFlix.usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    
    
    public String login(LoginDTO loginDTO) {


        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.senha())
                );
        } catch (Exception e) {
            throw new RuntimeException("Credenciais invalidas", e);
        }
        
        String role = (usuarioRepository.findByEmail(loginDTO.login())
        				.orElseThrow(() ->  new RuntimeException("Usuario não encontrado.")).getAcessoADM())? "ADMIN" : "COMUM";
    
        String token = jwtUtil.gerarToken(loginDTO.login(), role);
        
        return token;
    }
    
}