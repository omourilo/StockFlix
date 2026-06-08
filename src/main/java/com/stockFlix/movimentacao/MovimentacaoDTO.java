package com.stockFlix.movimentacao;

import io.swagger.v3.oas.annotations.media.Schema;

public record MovimentacaoDTO(
    Long id,
    Boolean tipoMovimentacao,
    int qtdMovimentada,
    @Schema(example = "2025-05-27")
    String data,
    Long produtoId,
    String produtoNome,
    Long usuarioId,
    String usuarioNome
) {
    public MovimentacaoDTO(Movimentacao movimentacao) {
        this(
            movimentacao.getId(),
            movimentacao.getTipoMovimentacao(), 
            movimentacao.getQtdMovimentada(),
            movimentacao.getData().toString(),
            movimentacao.getProduto() != null ? movimentacao.getProduto().getId() : null,
            movimentacao.getProduto() != null ? movimentacao.getProduto().getNome() : null,
            movimentacao.getUsuario() != null ? movimentacao.getUsuario().getId() : null,
            movimentacao.getUsuario() != null ? movimentacao.getUsuario().getLogin() : null
        );
    }
} 