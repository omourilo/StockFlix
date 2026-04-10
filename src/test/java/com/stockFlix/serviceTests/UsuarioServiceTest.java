package com.stockFlix.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.stockFlix.usuario.UsuarioService;
import com.stockFlix.usuario.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*; 

import com.stockFlix.usuario.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepo;
    @Mock
    private PasswordEncoder passwordEncoder; 

    @InjectMocks
    private UsuarioService usuarioService; 

    @Test
    void testCreateUsuario() {
        //Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO("Ramon@email", "1234", true);

        Usuario usuarioEntity = new Usuario(1L, "Ramon@email", "1234", true);

        when(usuarioRepo.save(any(Usuario.class))).thenReturn(usuarioEntity);

        //Act
        UsuarioDTO resultadoDTO = usuarioService.createUsuario(usuarioDTO);

        //Assert
        assertEquals("Ramon@email", resultadoDTO.login()); 
        assertEquals("1234", resultadoDTO.senha());
        assertEquals(true, resultadoDTO.acessoADM());

    }
    
}