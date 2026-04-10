package com.stockFlix.estoque;

import com.stockFlix.excecoes.NotFoundException;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepo; 

    EstoqueService(EstoqueRepository estoqueRepo) {
        this.estoqueRepo = estoqueRepo;
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

    public void deleteEstoque(long id) {
        estoqueRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Estoque não encontrado!"));
        estoqueRepo.deleteById(id); 
    }

}