package com.stockFlix.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockFlix.DTOs.UsuarioDTO;
import com.stockFlix.Services.UsuarioService;


/**
 * Contralador para mapeamento dos endpoints do usuario.
 * 
 * <p>Expõe os endPoints do usuario para conexão com o frontEnd</p>
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping
	public List<UsuarioDTO> getAllUsuarios(){
		return usuarioService.readAllUsuarios();
	}
	
	@GetMapping("/{id}")
	public UsuarioDTO getUsuarioById(@PathVariable Long id) {
		return usuarioService.findById(id);
	}
	
	@PostMapping
	public UsuarioDTO createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		return usuarioService.createUsuario(usuarioDTO);
	}
	
	@PutMapping("/{id}")
	public UsuarioDTO updateUsuario(
			@PathVariable Long id,
			@RequestBody UsuarioDTO usuarioDTO) {
		return usuarioService.updateUsuario(id, usuarioDTO);
	}
	
	@DeleteMapping("/{id}")
	public void deleteUsuario(@PathVariable Long id) {
		usuarioService.deleteUsuario(id);
	}

}
