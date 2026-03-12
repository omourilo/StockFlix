package com.stockFlix.DTOs;

import com.stockFlix.Models.Usuario;

public record UsuarioDTO(
		Long id,
		String login,
		String senha,
		Boolean acessoADM
		) {
	
	public UsuarioDTO(Usuario usuario) {
		this(
				usuario.getId(),
				usuario.getLogin(),
				usuario.getSenha(),
				usuario.getAcessoADM());
	}

}
