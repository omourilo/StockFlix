package com.stockFlix.movimentacao;

public record MovimentacaoDTO(
    Long id,
    Boolean tipoMovimentacao,
    int qtdMovimentada,
    String data,
    Long produtoId,
    Long usuarioId
) {
    public MovimentacaoDTO(Movimentacao movimentacao) {
        this(
            movimentacao.getId(),
            movimentacao.getTipoMovimentacao(), 
            movimentacao.getQtdMovimentada(),
            movimentacao.getData().toString(),
            movimentacao.getProduto() != null ? movimentacao.getProduto().getId() : null,
            movimentacao.getUsuario() != null ? movimentacao.getUsuario().getId() : null
        );
    }
} 