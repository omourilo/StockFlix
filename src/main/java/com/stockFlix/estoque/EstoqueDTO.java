package com.stockFlix.estoque;

public record EstoqueDTO(
    Long id,
    String nome,
    Boolean ativo
) {
    public EstoqueDTO(Estoque estoque) {
        this (
            estoque.getId(),
            estoque.getNome(),
            estoque.getAtivo()
        ); 
    }
} 