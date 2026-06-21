
package com.stockFlix.auth;

/**
 * Objeto para transferencia de dados de Login.
 * 
 * <p>É um DTO(Data Transfer Object) que transcreve a resposta da API
 * que vem do frontEnd para um objeto em java e vise versa, evitando que a entidade 
 * se exponha diretamente ao front.</p>
 * 
 */
public record LoginDTO(String login, String senha){}