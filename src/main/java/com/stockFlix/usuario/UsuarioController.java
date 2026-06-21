package com.stockFlix.usuario;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * Contralador para mapeamento dos endpoints do usuario.
 * 
 * <p>Expõe os endPoints do usuario	permitindo que o front realize HTTP Requests.</p>
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
	    this.usuarioService = usuarioService;
	}
	
    /**
     * Retorna todos os usuários cadastrados.
     *
     * @return lista de {@code UsuarioDTO}
     */
	@GetMapping
	public List<UsuarioDTO> getAllUsuarios(){
		return usuarioService.readAllUsuarios();
	}
	
    /**
     * Busca um usuário pelo ID.
     *
     * @param id identificador do usuário
     * @return {@code UsuarioDTO} correspondente ao usuário encontrado
     */
	@GetMapping("/{id}")
	public UsuarioDTO getUsuarioById(@PathVariable Long id) {
		return usuarioService.findById(id);
	}
	
    /**
     * Cria um novo usuário.
     *
     * @param usuarioDTO dados do usuário a ser criado
     * @return {@see ResponseEntity<>} com resposta 201 e contendo os dados persistidos
     */
	@PostMapping
	public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUsuario(usuarioDTO));
	}
	
    /**
     * Atualiza um usuário existente.
     *
     * @param id identificador do usuário
     * @param usuarioDTO dados atualizados do usuário
     * @return {@code UsuarioDTO} com os dados atualizados
     */
	@PutMapping("/{id}")
	public ResponseEntity<UsuarioDTO> updateUsuario(
			@PathVariable Long id,
			@RequestBody UsuarioDTO usuarioDTO) {
		return ResponseEntity.ok().body(usuarioService.updateUsuario(id, usuarioDTO));
	}
	
    /**
     * Remove um usuário pelo ID.
     *
     * @param id identificador do usuário
     */
	@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
	public void deleteUsuario(@PathVariable Long id) {
		usuarioService.deleteUsuario(id);
	}

}
