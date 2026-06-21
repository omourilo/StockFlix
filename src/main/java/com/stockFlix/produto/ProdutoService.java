package com.stockFlix.produto;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stockFlix.excecoes.NotFoundException;
import com.stockFlix.excecoes.PopulatedDeleteException;
import com.stockFlix.setor.SetorRepository;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
    
    private final ProdutoRepository produtoRepo;
    private final SetorRepository setorRepo; 

    public ProdutoService(
        ProdutoRepository produtoRepo,
        SetorRepository setorRepo
    ) { 
        this.produtoRepo = produtoRepo;
        this.setorRepo = setorRepo;
    }

    public ProdutoDTO createProduto(ProdutoDTO produtoDTO) {
        Produto produtoEntity = new Produto(produtoDTO);

        if (produtoDTO.setorId() != null) {
            produtoEntity.setSetor(setorRepo.findById(produtoDTO.setorId())
                    .orElseThrow(() -> new NotFoundException("Setor não encontrado!")));
        }

        return new ProdutoDTO(produtoRepo.save(produtoEntity));
    }

    @Transactional
    public ProdutoDTO updateProduto(long id, ProdutoDTO produtoDTO) {
        Produto produtoEntity = produtoRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado!"));
        
        produtoEntity.setNome(produtoDTO.nome());
        produtoEntity.setPreco(produtoDTO.preco()); 
        produtoEntity.setDescricao(produtoDTO.descricao()); 
        produtoEntity.setSetor(setorRepo.findById(produtoDTO.setorId())
                    .orElseThrow(() -> new NotFoundException("Setor não encontrado!")));
        
        return new ProdutoDTO(produtoEntity);
    }

    public ProdutoDTO readProdutoById(long id) {
        return new ProdutoDTO(produtoRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado!")));
    }

    public List<ProdutoDTO> readAllProdutos() {
        return produtoRepo.findAll()
                    .stream()
                    .map(produto -> new ProdutoDTO(produto))
                    .toList();
    }

    public List<ProdutoDTO> readAllProdutosBySetorId(long id) {
        return produtoRepo.findAllBySetorId(id)
                    .stream()
                    .map(produto -> new ProdutoDTO(produto))
                    .toList();
    }


    public void deleteProduto(long id) {
        Produto produtoEntity = produtoRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado!"));
        if (!produtoEntity.getMovimentacoes().isEmpty() || !produtoEntity.getPrevisoes().isEmpty()) {
            throw new PopulatedDeleteException(
                "Impossivel deletar, produto com " + produtoEntity.getMovimentacoes().size() + 
                " movimentações e " + produtoEntity.getPrevisoes().size() + " previsões que serão afetados");
        }
        produtoRepo.deleteById(id);
    }
}
