package com.stockFlix.produto;

public record ProdutoDTO(
    String nome,
    float preco,
    int quantidade,
    String descricao,
    Long setorId
) {
    public ProdutoDTO(Produto produto) {
        this(
            produto.getNome(),
            produto.getPreco(),
            produto.getQuantidade(),
            produto.getDescricao(),
            produto.getSetor() != null ? produto.getSetor().getId() : null
        );
    }
}
