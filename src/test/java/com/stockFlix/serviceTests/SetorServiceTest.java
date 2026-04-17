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
import com.stockFlix.estoque.EstoqueRepository;
import com.stockFlix.excecoes.NotFoundException;
import com.stockFlix.setor.Setor;
import com.stockFlix.setor.SetorDTO;
import com.stockFlix.setor.SetorRepository;
import com.stockFlix.setor.SetorService;

@ExtendWith(MockitoExtension.class)
class SetorServiceTest {
    
    @Mock
    private SetorRepository setorRepo;
    @Mock
    private EstoqueRepository estoqueRepo;

    @InjectMocks
    private SetorService setorService;

    @Test
    void testCreateSetor() {

        Estoque estoqueEntity = new Estoque(1L, "Estoque_1" , new ArrayList<>());

        Setor setorEntity = new Setor(1L, "Setor_1", new ArrayList<>(), estoqueEntity);

        SetorDTO setorDTO = new SetorDTO("Setor_1", 1L); 

        when(estoqueRepo.findById(anyLong())).thenReturn(Optional.of(estoqueEntity));
        when(setorRepo.save(any(Setor.class))).thenReturn(setorEntity);


        SetorDTO resultadoDTO = setorService.createSetor(setorDTO);


        assertEquals("Setor_1", resultadoDTO.nome());
        assertEquals(1L, resultadoDTO.estoqueId());
    }

    @Test
    void testCreateSetorEstoqueNaoExiste() {

        SetorDTO setorDTO = new SetorDTO("Setor_1", 1L); 

        when(estoqueRepo.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> setorService.createSetor(setorDTO));
        System.err.println(ex.getMessage());
    }

    @Test
    void testUpdateSetor() {
        Estoque estoqueEntity = new Estoque(2L, "Estoque_1" , new ArrayList<>());

        Setor setorEntity = new Setor(1L, "Setor_1", new ArrayList<>(), estoqueEntity);

        SetorDTO setorDTO = new SetorDTO("Setor_10", 2L); 


        when(setorRepo.findById(anyLong())).thenReturn(Optional.of(setorEntity));
        when(estoqueRepo.findById(anyLong())).thenReturn(Optional.of(estoqueEntity));
        when(setorRepo.save(any(Setor.class))).thenReturn(setorEntity);


        SetorDTO resultadoDTO = setorService.updateSetor(1L, setorDTO);


        assertEquals("Setor_10", resultadoDTO.nome());
        assertEquals(2L, resultadoDTO.estoqueId());
    }

    @Test
    void testUpdateSetorEstoqueNaoExiste() {

        Setor setorEntity = new Setor(1L, "Setor_1", new ArrayList<>(), new Estoque());
        SetorDTO setorDTO = new SetorDTO("Setor_1", 1L); 

        when(setorRepo.findById(anyLong())).thenReturn(Optional.of(setorEntity));
        when(estoqueRepo.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> setorService.updateSetor(1L, setorDTO));
        System.err.println(ex.getMessage());
    }

    @Test
    void testUpdateSetorNaoExiste() {

        SetorDTO setorDTO = new SetorDTO("Setor_1", 1L); 

        when(setorRepo.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> setorService.updateSetor(1L, setorDTO));
        System.err.println(ex.getMessage());
    }

    @Test
    void testDeleteSetor() {
        Setor setorEntity = new Setor(1L, "Setor_1", new ArrayList<>(), new Estoque());

        when(setorRepo.findById(anyLong())).thenReturn(Optional.of(setorEntity));
        doNothing().when(setorRepo).deleteById(anyLong());

        setorService.deleteSetor(1L);

        verify(setorRepo, times(1)).deleteById(1L);  
    }


    @Test
    void testDeleteSetorNaoEncontrado() {
        when(setorRepo.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> setorService.deleteSetor(1L));
        System.err.println(ex.getMessage());

    }

}
