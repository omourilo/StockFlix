package com.stockFlix.serviceTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stockFlix.estoque.Estoque;
import com.stockFlix.estoque.EstoqueDTO;
import com.stockFlix.estoque.EstoqueRepository;
import com.stockFlix.estoque.EstoqueService;

@ExtendWith(MockitoExtension.class)
class EstoqueServiceTest {
    
    @Mock
    private EstoqueRepository estoqueRepo;

    @InjectMocks
    private EstoqueService estoqueService;

    @Test
    void testCreateEstoque() {
        //Arrange
        EstoqueDTO estoqueDTO = new EstoqueDTO(1L, "Estoque_1");

        Estoque estoqueEntity = new Estoque(1L, "Estoque_1", new ArrayList<>());

        when(estoqueRepo.save(any(Estoque.class))).thenReturn(estoqueEntity);

        //Act
        EstoqueDTO resultaDTO = estoqueService.createEstoque(estoqueDTO);

        //Assert
        assertEquals("Estoque_1", resultaDTO.nome());
        
    }
}
