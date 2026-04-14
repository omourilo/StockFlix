package com.stockFlix.setor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SetorRepository extends JpaRepository<Setor, Long> {
	
	List<Setor> findAllByEstoqueId(long id);
}
