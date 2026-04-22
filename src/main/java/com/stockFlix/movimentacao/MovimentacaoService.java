package com.stockFlix.movimentacao;

import org.springframework.stereotype.Service;

import com.stockFlix.excecoes.InsufficientStockException;
import com.stockFlix.excecoes.NotFoundException;
import com.stockFlix.produto.Produto;
import com.stockFlix.produto.ProdutoRepository;

@Service
public class MovimentacaoService {
    
    private final MovimentacaoRepository movimentacaoRepo;
    private final ProdutoRepository produtoRepo;

    public MovimentacaoService(
        MovimentacaoRepository movimentacaoRepo,
        ProdutoRepository produtoRepo
    ) {
        this.movimentacaoRepo = movimentacaoRepo;
        this.produtoRepo = produtoRepo;
    }

    public MovimentacaoDTO createMovimentacao(MovimentacaoDTO movimentDTO) {
        Movimentacao movimentEntity = new Movimentacao(movimentDTO);

        if(movimentDTO.produtoId() != null) {
            movimentEntity.setProduto(produtoRepo.findById(movimentDTO.produtoId())
                .orElseThrow(() -> new NotFoundException("Setor não encontrado!")));
        }

        //Se for true é adição
        if (movimentEntity.getTipoMovimentacao()) {
            Produto produtoEntity = produtoRepo.findById(movimentDTO.produtoId())
                        .orElseThrow(() -> new NotFoundException("Produto não encontrado!")); 
            produtoEntity.adicionarQuantidade(movimentDTO.qtdMovimentada());
        }else {
            try {
                Produto produtoEntity = produtoRepo.findById(movimentDTO.produtoId())
                             .orElseThrow(() -> new NotFoundException("Produto não encontrado!")); 
            produtoEntity.removerQuantidade(movimentDTO.qtdMovimentada());
            } catch (InsufficientStockException e) {
               System.err.println(e);
            }
        }

        return new MovimentacaoDTO(movimentacaoRepo.save(movimentEntity));
    }
}
