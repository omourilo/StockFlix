package com.stockFlix.movimentacao;

public record MovimentacaoDTO(
    Boolean tipoMovimentacao,
    int qtdMovimentada,
    String data,
    Long produtoId
) {
    public MovimentacaoDTO(Movimentacao movimentacao) {
        this(
            movimentacao.getTipoMovimentacao(), 
            movimentacao.getQtdMovimentada(),
            movimentacao.getData().toString(),
            movimentacao.getProduto() != null ? movimentacao.getProduto().getId() : null
        );
    }
} 