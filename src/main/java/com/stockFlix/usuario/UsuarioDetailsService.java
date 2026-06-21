package com.stockFlix.usuario;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


/**
 * Serviço responsável por carregar os dados do usuário para autenticação.
 * 
 * <p>Esta classe implementa a interface {@link UserDetailsService}, que é
 * utilizada pelo Spring Security para buscar informações do usuário
 * durante o processo de login.</p>
 * 
 * <p>Segue o princípio de Inversão de Dependência (SOLID), pois depende
 * de uma abstração ({@link UsuarioRepository}) para acessar os dados.</p>
 */
@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {
	
	private final UsuarioRepository usuarioRepo;

	/**
	 * Carrega um usuário com base no login informado.
	 * 
	 * <p>Este método é chamado automaticamente pelo Spring Security
	 * durante o processo de autenticação.</p>
	 * 
	 * @param login Nome de usuário utilizado para login.
	 * @return {@link UserDetails} contendo os dados do usuário autenticado.
	 * @throws UsernameNotFoundException Caso o usuário não seja encontrado.
	 */
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepo.findByLogin(login)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado para:" +login));
		
		return usuario;
	}
	
}
