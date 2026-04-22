package com.stockFlix.movimentacao;

import org.springframework.stereotype.Service;

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
        Produto produtoEntity = produtoRepo.findById(movimentDTO.produtoId())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado!"));


        movimentEntity.setProduto(produtoEntity);
        
        //Se for true é adição
        if (movimentEntity.getTipoMovimentacao()) {
            produtoEntity.adicionarQuantidade(movimentDTO.qtdMovimentada());
        }else {
        	produtoEntity.removerQuantidade(movimentDTO.qtdMovimentada());
        }

        produtoRepo.save(produtoEntity);
        return new MovimentacaoDTO(movimentacaoRepo.save(movimentEntity));
    }
}
