package com.stockFlix.movimentacao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    
    List<Movimentacao> findByDataBetween(LocalDate dataInicio, LocalDate dataFim); 
}
