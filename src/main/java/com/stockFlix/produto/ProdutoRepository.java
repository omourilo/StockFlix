package com.stockFlix.produto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAllBySetorId(long id);
}
