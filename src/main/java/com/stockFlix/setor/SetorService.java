package com.stockFlix.setor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stockFlix.estoque.Estoque;
import com.stockFlix.estoque.EstoqueRepository;
import com.stockFlix.excecoes.DisabledEntityException;
import com.stockFlix.excecoes.NotFoundException;
import com.stockFlix.produto.Produto;
import com.stockFlix.produto.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class SetorService {
	
	
	private final SetorRepository setorRepo;
	private final EstoqueRepository estoqueRepo;
	private final ProdutoRepository produtoRepo;
	
	
	public SetorService(
			SetorRepository setorRepo, 
			EstoqueRepository estoqueRepo,
			ProdutoRepository produtoRepo
		) {
		this.setorRepo = setorRepo;
		this.estoqueRepo = estoqueRepo;
		this.produtoRepo = produtoRepo;
	}
	
	public SetorDTO createSetor(SetorDTO setorDTO) {
		Setor setorEntity = new Setor(setorDTO); 
		if(setorDTO.estoqueId() != null) {
			Estoque estoqueEntity = estoqueRepo.findById(setorDTO.estoqueId())
					.orElseThrow(() -> new NotFoundException("Estoque não encontrado"));
			if (!estoqueEntity.getAtivo()) throw new DisabledEntityException("Estoque inativo!!");
			setorEntity.setEstoque(estoqueEntity);
		}
		
		return new SetorDTO(setorRepo.save(setorEntity));		
	}
	
	@Transactional
	public SetorDTO updateSetor(Long id, SetorDTO setorDTO) {
		Setor setorEntity = setorRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Setor não encontrado!"));
		
		setorEntity.setNome(setorDTO.nome());
		setorEntity.setEstoque(estoqueRepo.findById(setorDTO.estoqueId())
				.orElseThrow(() -> new NotFoundException("Estoque não encontrado!")));
		
		return new SetorDTO(setorRepo.save(setorEntity));
	}
	
	public SetorDTO readSetorById(Long id) {
		return new SetorDTO(setorRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Setor não encontrado!")));
	}
	
	public List<SetorDTO> readAllSetores() {
		return setorRepo.findAll()
				.stream()
				.map(setor -> new SetorDTO(setor))
				.toList();
	}
	
	public List<SetorDTO> readSetoresByEstoqueId(long id) {
		return setorRepo.findAllByEstoqueId(id)
				.stream()
				.map(setor -> new SetorDTO(setor))
				.toList();
	}
	
	@Transactional
	public void desativarSetor(Long id) {
		Setor setorEntity = setorRepo.findById(id)
						.orElseThrow(() -> new NotFoundException("Usuario não encontrado!!"));
		
		List<Produto> produtos = produtoRepo.findAllBySetorId(id);
		produtos.stream().forEach(p -> p.setAtivo(false));
		produtoRepo.saveAll(produtos);
		
		setorEntity.setAtivo(false);
		setorRepo.save(setorEntity);
	}
	
	@Transactional
	public void ativarSetor(Long id) {
		Setor setorEntity = setorRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Usuario não encontrado!!"));

		List<Produto> produtos = produtoRepo.findAllBySetorId(id);
		produtos.stream().forEach(p -> p.setAtivo(true));
		produtoRepo.saveAll(produtos);
		
		setorEntity.setAtivo(true);
		setorRepo.save(setorEntity);
	}
	
}
