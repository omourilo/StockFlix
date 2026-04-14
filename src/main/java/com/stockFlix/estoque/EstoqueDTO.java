package com.stockFlix.estoque;

public record EstoqueDTO(
    Long id,
    String nome
) {
    public EstoqueDTO(Estoque estoque) {
        this (
            estoque.getId(),
            estoque.getNome()
        ); 
    }
} 