package com.stockFlix.DTOs;

import com.stockFlix.Models.Usuario;


/**
 * Objeto para transferencia de dados da entidade Usuario.
 * 
 * <p>É um DTO(Data Transfer Object) que transcreve a resposta da API
 * que vem do frontEnd para um objeto em java e vise versa, evitando que a entidade 
 * se exponha diretamente ao front.</p>
 * 
 */
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
