package com.stockFlix.setor;

public record SetorDTO(
    long id,
    String nome,
    Long estoqueId
) {
    public SetorDTO(Setor setor) {
        this(
            setor.getId(),
            setor.getNome(),
            setor.getEstoque() != null ? setor.getEstoque().getId() : null
        );
    }
}
