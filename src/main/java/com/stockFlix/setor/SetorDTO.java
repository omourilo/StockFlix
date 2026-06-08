package com.stockFlix.setor;

public record SetorDTO(
    Long id,
    String nome,
    Boolean ativo,
    Long estoqueId,
    String estoqueNome
) {
    public SetorDTO(Setor setor) {
        this(
            setor.getId(),
            setor.getNome(),
            setor.getAtivo(),
            setor.getEstoque() != null ? setor.getEstoque().getId() : null,
            setor.getEstoque() != null ? setor.getEstoque().getNome() : null 
        );
    }
}
