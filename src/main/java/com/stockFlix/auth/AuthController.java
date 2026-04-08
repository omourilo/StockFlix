package com.stockFlix.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

/**
 * Contralador para mapeamento dos endpoints do login.
 * 
 * <p>Expõe o endPoint do login permitindo que o front realize HTTP Requests.</p>
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	/**
	 * Realiza o login.
	 * 
	 * @param loginDTO dados de login passado.
	 * @return ResponseEntity resposta da requisição, é esperado resposta 200
	 */
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {

		authService.login(loginDTO, response);
		return ResponseEntity.ok("Login realizado com sucesso!");
	}
}
