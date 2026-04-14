package com.stockFlix.auth;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.stockFlix.usuario.UsuarioRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Serviço responsável pelo processo de autenticação de usuários.
 * 
 * <p>Realiza a validação das credenciais, geração do token JWT
 * e envio do token via cookie na resposta HTTP.</p>
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    
    /**
     * Realiza o login do usuário.
     * 
     * <p>Fluxo:</p>
     * <ol>
     *   <li>Valida login e senha</li>
     *   <li>Busca o usuário no banco</li>
     *   <li>Define a role do usuário</li>
     *   <li>Gera o token JWT</li>
     *   <li>Adiciona o token como cookie na resposta</li>
     * </ol>
     * 
     * @param loginDTO objeto contendo login e senha
     * @param response resposta HTTP (para adicionar o cookie)
     * @return token JWT gerado
     */
    public String login(LoginDTO loginDTO, HttpServletResponse response) {


        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.senha())
                );
        } catch (Exception e) {
            throw new RuntimeException("Credenciais invalidas", e);
        }
        
        String role = (usuarioRepository.findByLogin(loginDTO.login())
        				.orElseThrow(() ->  new RuntimeException("Usuario não encontrado.")).getAcessoADM())? "ADMIN" : "COMUM";
    
        String token = jwtUtil.gerarToken(loginDTO.login(), role);

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                                        .httpOnly(true)
                                        .path("/")
                                        .build();
        
        response.addHeader("Set-Cookie", cookie.toString());
        
        return token;
    }
    
}