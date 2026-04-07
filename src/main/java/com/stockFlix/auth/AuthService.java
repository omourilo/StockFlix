package com.stockFlix.auth;

import org.springframework.stereotype.Service;

import com.stockFlix.usuario.UsuarioRepository;

@Service
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String login(LoginDTO loginDTO) {

        String login = loginDTO.login();
        String senha = loginDTO.senha();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login, senha)
                );
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}