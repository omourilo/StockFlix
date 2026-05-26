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
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stockFlix.estoque.Estoque;
import com.stockFlix.excecoes.NotFoundException;
import com.stockFlix.excecoes.PopulatedDeleteException;
import com.stockFlix.movimentacao.Movimentacao;
import com.stockFlix.previsao.Previsao;
import com.stockFlix.produto.Produto;
import com.stockFlix.produto.ProdutoDTO;
import com.stockFlix.produto.ProdutoRepository;
import com.stockFlix.produto.ProdutoService;
import com.stockFlix.setor.Setor;
import com.stockFlix.setor.SetorRepository;


@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepo;
    @Mock
    private SetorRepository setorRepo;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void testCreateProduto() {

        Estoque estoqueEntity = new Estoque(1L, "Estoque_1" , new ArrayList<>()); 

        Setor setorEntity = new Setor(1L, "Setor_1", new ArrayList<>(), estoqueEntity);

        Produto produtoEntity = new Produto(1L, "Produto_1", 1.88F, 1, "Lorem impsum", setorEntity, new ArrayList<>(), new ArrayList<>());

        ProdutoDTO produtoDTO = new ProdutoDTO(1L, "Produto_1", 1.88F, 1, "Lorem impsum", 1L);

        when(setorRepo.findById(anyLong())).thenReturn(Optional.of(setorEntity)); 
        when(produtoRepo.save(any(Produto.class))).thenReturn(produtoEntity);

        ProdutoDTO resultadoDTO = produtoService.createProduto(produtoDTO);
       
        assertEquals("Produto_1", resultadoDTO.nome());
        assertEquals(1.88F , resultadoDTO.preco(), 0);
        assertEquals(1L, resultadoDTO.quantidade());
        assertEquals("Lorem impsum", resultadoDTO.descricao());
        assertEquals(1L , resultadoDTO.setorId());
        verify(produtoRepo, times(1)).save(any(Produto.class));
    }

    @Test
    void testCreateProdutoSetorNaoExiste() {

        ProdutoDTO produtoDTO = new ProdutoDTO(1L, "Produto_1", 1.88F, 1, "Lorem impsum", 1L);

        when(setorRepo.findById(anyLong())).thenReturn(Optional.empty()); 

        NotFoundException ex = assertThrows(NotFoundException.class, () -> produtoService.createProduto(produtoDTO));
        System.err.println(ex.getMessage());
    }

    @Test
    void testUpdateProduto() {

        Estoque estoqueEntity = new Estoque(1L, "Estoque_1" , new ArrayList<>()); 
        Setor setorEntity = new Setor(1L, "Setor_1", new ArrayList<>(), estoqueEntity);
        Produto produtoEntity = new Produto(1L, "Produto_1", 1.88F, 1, "Lorem impsum", setorEntity, new ArrayList<>(), new ArrayList<>());
        ProdutoDTO produtoDTO = new ProdutoDTO(1L, "Produto_2", 3.21F, 10, "Impsum loren", 1L);

        when(produtoRepo.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(setorRepo.findById(anyLong())).thenReturn(Optional.of(setorEntity)); 
        when(produtoRepo.save(any(Produto.class))).thenReturn(produtoEntity);

        ProdutoDTO resultadoDTO = produtoService.updateProduto(1L, produtoDTO);
       
        assertEquals("Produto_2", resultadoDTO.nome());
        assertEquals(3.21F , resultadoDTO.preco(), 0);
        assertEquals("Impsum loren", resultadoDTO.descricao());
        assertEquals(1L , resultadoDTO.setorId());
        verify(produtoRepo, times(1)).save(any(Produto.class));
    }

    @Test
    void testUpdateProdutoSetorNaoExiste() {

        Produto produtoEntity = new Produto(1L, "Produto_1", 1.88F, 1, "Lorem impsum", new Setor(), new ArrayList<>(), new ArrayList<>());
        ProdutoDTO produtoDTO = new ProdutoDTO(1L, "Produto_1", 1.88F, 1, "Lorem impsum", 1L);
        
        when(produtoRepo.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(setorRepo.findById(anyLong())).thenReturn(Optional.empty()); 

        NotFoundException ex = assertThrows(NotFoundException.class, () -> produtoService.updateProduto(1L, produtoDTO)); 
        System.err.println(ex.getMessage());
    }

    @Test
    void testDeleteProduto() {

        Produto produtoEntity = new Produto(1L, "Produto_1", 1.88F, 1, "Lorem impsum", new Setor(), new ArrayList<>(), new ArrayList<>());

        when(produtoRepo.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        doNothing().when(produtoRepo).deleteById(anyLong());

        produtoService.deleteProduto(1L);

        verify(produtoRepo, times(1)).deleteById(1L); 
    }

    @Test
    void testDeleteProdutoNaoEcontrado() {

        when(produtoRepo.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> produtoService.deleteProduto(1L)); 
        System.err.println(ex.getMessage());

    }    

    @Test
    void testDeleteProdutoListasPopuladas() {

        Previsao previsaoEntity = new Previsao();
        Movimentacao movimentEntity = new Movimentacao();
        
        Produto produtoEntity = new Produto(1L, "Produto_1", 1.88F, 1, "Lorem impsum", 
                                    new Setor(), new ArrayList<>(List.of(movimentEntity)), new ArrayList<>(List.of(previsaoEntity)));

        when(produtoRepo.findById(anyLong())).thenReturn(Optional.of(produtoEntity));

        PopulatedDeleteException ex = assertThrows(PopulatedDeleteException.class, () -> produtoService.deleteProduto(1L)); 
        System.err.println(ex.getMessage());
    }
}