package com.stockFlix.estoque;

public record EstoqueDTO(
    long id,
    String nome
) {
    public EstoqueDTO(Estoque estoque) {
        this (
            estoque.getId(),
            estoque.getNome()
        ); 
    }
} 