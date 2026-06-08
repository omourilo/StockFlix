package com.stockFlix.produto;

public record ProdutoDTO(
    Long id,
    String nome,
    float preco,
    int quantidade,
    String descricao,
    Boolean ativo,
    Long setorId
) {
    public ProdutoDTO(Produto produto) {
        this(
            produto.getId(),
            produto.getNome(),
            produto.getPreco(),
            produto.getQuantidade(),
            produto.getDescricao(),
            produto.getAtivo(),
            produto.getSetor() != null ? produto.getSetor().getId() : null
        );
    }
}
