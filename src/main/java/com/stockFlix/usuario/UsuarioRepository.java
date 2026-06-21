package com.stockFlix.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repositório responsável pela interação com o banco de dados.
 *
 * Gerencia a persistencia de dados no banco da classe Usuario estendendo JpaRepository, 
 * interface padrão SpringBoot com as operações CRUD já implementadas 
 *
 * @see JpaRepository
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    //Query de pesquisa no banco de dados
    Optional<Usuario> findByLogin(String email);
    }
