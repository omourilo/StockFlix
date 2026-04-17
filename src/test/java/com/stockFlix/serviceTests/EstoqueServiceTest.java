package com.stockFlix.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stockFlix.estoque.Estoque;
import com.stockFlix.estoque.EstoqueDTO;
import com.stockFlix.estoque.EstoqueRepository;
import com.stockFlix.estoque.EstoqueService;
import com.stockFlix.excecoes.NotFoundException;
import com.stockFlix.excecoes.PopulatedDeleteException;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest { 
    
    @Mock
    private EstoqueRepository estoqueRepo;

    @InjectMocks
    private EstoqueService estoqueService;

    @Test
    void testCreateEstoque() {
        //Arrange
        EstoqueDTO estoqueDTO = new EstoqueDTO("Estoque_1");

        Estoque estoqueEntity = new Estoque(1L, "Estoque_1", new ArrayList<>());

        when(estoqueRepo.save(any(Estoque.class))).thenReturn(estoqueEntity);

        //Act
        EstoqueDTO resultaDTO = estoqueService.createEstoque(estoqueDTO);

        //Assert
        assertEquals("Estoque_1", resultaDTO.nome());
        
    }

    @Test 
    void testUpdateEstoque() {
        
        Estoque estoqueEntity = new Estoque(1L, "Estoque_1", new ArrayList<>());

        EstoqueDTO estoqueDTO = new EstoqueDTO("Estoque_10");

        when(estoqueRepo.findById(anyLong())).thenReturn(Optional.of(estoqueEntity));
        when(estoqueRepo.save(any(Estoque.class))).thenReturn(estoqueEntity);

        EstoqueDTO resultadoDTO = estoqueService.updateEstoque(1, estoqueDTO);

        assertEquals("Estoque_10", resultadoDTO.nome()); 

    }

    @Test 
    void testUpdateEstoqueNaoEncontrado() {
        
        EstoqueDTO estoqueDTO = new EstoqueDTO("Estoque_10");

        when(estoqueRepo.findById(anyLong())).thenReturn(Optional.empty()); 
        
        NotFoundException ex = assertThrows(NotFoundException.class, () -> estoqueService.updateEstoque(1L, estoqueDTO));
        System.err.println(ex.getMessage());
 
    }

    @Test
    void testDeleteEstoque() {

        Estoque estoqueEntity = new Estoque(1L, "Estoque_1", new ArrayList<>());

        when(estoqueRepo.findById(anyLong())).thenReturn(Optional.of(estoqueEntity));
        doNothing().when(estoqueRepo).deleteById(anyLong());

        estoqueService.deleteEstoque(1L);

        verify(estoqueRepo, times(1)).deleteById(1L); 
    }

    @Test
    void testDeleteEstoqueNaoEncontrado() {

        when(estoqueRepo.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> estoqueService.deleteEstoque(1L));
        System.err.println(ex.getMessage());
    }

    @Test
    void testDeleteEstoquePopulado() {
        Estoque estoqueEntity = new Estoque(1L, "Estoque_1", new ArrayList<>());

        when(estoqueRepo.findById(anyLong())).thenReturn(Optional.of(estoqueEntity));
        when(estoqueRepo.existsByEstoqueId(anyLong())).thenReturn(true);

        PopulatedDeleteException ex = assertThrows(PopulatedDeleteException.class, () -> estoqueService.deleteEstoque(1L));
        System.err.println(ex.getMessage());
    }

}
