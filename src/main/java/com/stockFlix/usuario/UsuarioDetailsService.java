package com.stockFlix.usuario;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {
	
	private final UsuarioRepository usuarioRepo;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepo.findByEmail(login)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado para:" +login));
		
		return usuario;
	}
	
}
