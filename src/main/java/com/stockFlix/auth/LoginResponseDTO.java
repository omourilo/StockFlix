package com.stockFlix.auth;

import com.stockFlix.usuario.Usuario;

public record LoginResponseDTO (Long id, String login, Boolean acessoADM){
    public LoginResponseDTO(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getLogin(),
            usuario.getAcessoADM());
    }
}
