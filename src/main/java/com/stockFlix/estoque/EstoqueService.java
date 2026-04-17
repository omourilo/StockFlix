package com.stockFlix.estoque;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stockFlix.excecoes.NotFoundException;
import com.stockFlix.excecoes.PopulatedDeleteException;

import jakarta.transaction.Transactional;

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
        Estoque estoqueEntity = estoqueRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Estoque não encontrado!"));
        if (!estoqueEntity.getSetores().isEmpty()) { 
            throw new PopulatedDeleteException("Impossivel deletar, estoque com " + estoqueEntity.getSetores().size() + " setores que serão afetados");
        }

        estoqueRepo.deleteById(id); 
    }

}