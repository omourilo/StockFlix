package com.stockFlix.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockFlix.Models.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{

}
