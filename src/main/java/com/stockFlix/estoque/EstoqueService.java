package com.stockFlix.estoque;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.stockFlix.excecoes.NotFoundException;
import com.stockFlix.produto.Produto;
import com.stockFlix.produto.ProdutoRepository;
import com.stockFlix.setor.Setor;
import com.stockFlix.setor.SetorRepository;

import jakarta.transaction.Transactional;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepo; 
    private final SetorRepository setorRepo;
    private final ProdutoRepository produtoRepo;

    EstoqueService(
    				EstoqueRepository estoqueRepo,
    				SetorRepository setorRepo,
    				ProdutoRepository produtoRepo
    				) {
        this.estoqueRepo = estoqueRepo;
        this.setorRepo = setorRepo;
        this.produtoRepo = produtoRepo;
        
    }


    public EstoqueDTO createEstoque(EstoqueDTO estoqueDTO) {
        Estoque estoqueEntity = new Estoque(estoqueDTO);
        return new EstoqueDTO(estoqueRepo.save(estoqueEntity));
    }

    @Transactional
    public EstoqueDTO updateEstoque(long id, EstoqueDTO estoqueDTO) {
        Estoque estoqueEntity = estoqueRepo.findById(id)
                                        .orElseThrow(() -> new NotFoundException("Estoque não encontrado!"));

        estoqueEntity.setNome(estoqueDTO.nome());
        estoqueRepo.save(estoqueEntity); 
        return new EstoqueDTO(estoqueEntity);
    }

    public List<EstoqueDTO> readAllEstoques() {
        return estoqueRepo.findAll()
                            .stream()
                            .map(estoque -> new EstoqueDTO(estoque))
                            .toList();
    }

    public EstoqueDTO findEstoqueById(long id) {
        return new EstoqueDTO(estoqueRepo.findById(id)
                                .orElseThrow(() -> new NotFoundException("Estoque não encontrado!")));
    }

    @Transactional
	public void desativarEstoque(Long id) {

		Estoque estoqueEntity = estoqueRepo.findById(id)
						.orElseThrow(() -> new NotFoundException("Estoque não encontrado!!"));
		
		List<Setor> setores = setorRepo.findAllByEstoqueId(id);
		setores.stream().forEach(setor -> setor.setAtivo(false));
		setorRepo.saveAll(setores);
		
		List<Produto> produtosInativados = new ArrayList<>();
		for (Setor setor : setores) {
			List<Produto> produtos = produtoRepo.findAllBySetorId(setor.getId());
			produtos.stream().forEach(p -> p.setAtivo(false));
			produtosInativados.addAll(produtos);
		}
		produtoRepo.saveAll(produtosInativados);
		
		estoqueEntity.setAtivo(false);
		estoqueRepo.save(estoqueEntity);
	}
	
    @Transactional
	public void ativarEstoque(Long id) {
		Estoque estoqueEntity = estoqueRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Estoque não encontrado!!"));

		List<Setor> setores = setorRepo.findAllByEstoqueId(id);
		setores.stream().forEach(setor -> setor.setAtivo(true));
		setorRepo.saveAll(setores);
		
		List<Produto> produtosAtivados = new ArrayList<>();
		for (Setor setor : setores) {
			List<Produto> produtos = produtoRepo.findAllBySetorId(setor.getId());
			produtos.stream().forEach(p -> p.setAtivo(true));
			produtosAtivados.addAll(produtos);
		}
		produtoRepo.saveAll(produtosAtivados);
		
		estoqueEntity.setAtivo(true);
		estoqueRepo.save(estoqueEntity);
	}


}