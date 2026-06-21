package com.stockFlix.previsao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrevisaoRepository extends JpaRepository<Previsao, Long> {

	List<Previsao> findByProdutoId(long id);
}
