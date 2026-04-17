package com.stockFlix.estoque;

public record EstoqueDTO(
    String nome
) {
    public EstoqueDTO(Estoque estoque) {
        this (
            estoque.getNome()
        ); 
    }
} 