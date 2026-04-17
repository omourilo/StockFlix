package com.stockFlix.setor;

public record SetorDTO(
    String nome,
    Long estoqueId
) {
    public SetorDTO(Setor setor) {
        this(
            setor.getNome(),
            setor.getEstoque() != null ? setor.getEstoque().getId() : null
        );
    }
}
