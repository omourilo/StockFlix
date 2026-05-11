package com.stockFlix.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import com.stockFlix.excecoes.LoginAlreadyExistsException;
import com.stockFlix.excecoes.NotFoundException;
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
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO("Ramon@email", "1234", true); 

        Usuario usuarioEntity = new Usuario(1L, "Ramon@email", "1234", true);

        when(usuarioRepo.save(any(Usuario.class))).thenReturn(usuarioEntity);

        // Act
        UsuarioDTO resultadoDTO = usuarioService.createUsuario(usuarioDTO);

        // Assert
        assertEquals("Ramon@email", resultadoDTO.login());
        assertEquals("1234", resultadoDTO.senha());
        assertEquals(true, resultadoDTO.acessoADM());

    }

    @Test
    void testCreateUsuarioLoginJaExiste() {
        UsuarioDTO usuarioDTO = new UsuarioDTO("Ramon@email", "1234", true);

        Usuario usuarioEntity = new Usuario();

        when(usuarioRepo.findByLogin(anyString())).thenReturn(Optional.of(usuarioEntity));
        

        LoginAlreadyExistsException ex = assertThrows(LoginAlreadyExistsException.class, () -> usuarioService.createUsuario(usuarioDTO));
        System.err.println(ex.getMessage());
    }

    @Test
    void testUpdateUsuario() {
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO("Ramon@email", "1234", true); 

        Usuario usuarioEntity = new Usuario(1L, "Ramon@email", "1234", true);

        when(usuarioRepo.findById(anyLong())).thenReturn(Optional.of(usuarioEntity));
        when(passwordEncoder.encode(anyString())).thenReturn("Senha_secreta");
        when(usuarioRepo.save(any(Usuario.class))).thenReturn(usuarioEntity);

        // Act
        UsuarioDTO resultadoDTO = usuarioService.updateUsuario(1L, usuarioDTO);

        // Assert
        assertEquals("Ramon@email", resultadoDTO.login());
        assertEquals("Senha_secreta", resultadoDTO.senha());
        assertEquals(true, resultadoDTO.acessoADM());
    }

    @Test
    void testUpdateUsuarioNaoEncontrado() {
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO("Ramon@email", "1234", true); 

        when(usuarioRepo.findById(anyLong())).thenReturn(Optional.empty());
        

        NotFoundException ex = assertThrows(NotFoundException.class, () -> usuarioService.updateUsuario(1L, usuarioDTO));
        System.err.println(ex.getMessage());
    }

    @Test
    void testDeleteUsuario() {
        Usuario usuarioEntity = new Usuario(1L, "Ramon@email", "1234", true);

        when(usuarioRepo.findById(anyLong())).thenReturn(Optional.of(usuarioEntity));
        doNothing().when(usuarioRepo).deleteById(1L);

        usuarioService.deleteUsuario(1L);

        verify(usuarioRepo, times(1)).deleteById(1L); 
       
    }

        @Test
    void testDeleteUsuarioNaoEncontrado() {

        when(usuarioRepo.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> usuarioService.deleteUsuario(1L));
        System.err.println(ex.getMessage());
    }


}