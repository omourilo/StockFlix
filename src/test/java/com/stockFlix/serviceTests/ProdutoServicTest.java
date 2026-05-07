package com.stockFlix.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

        ProdutoDTO produtoDTO = new ProdutoDTO("Produto_1", 1.88F, 1, "Lorem impsum", 1L);

        when(setorRepo.findById(anyLong())).thenReturn(Optional.of(setorEntity)); 
        when(produtoRepo.save(any(Produto.class))).thenReturn(produtoEntity);

        ProdutoDTO resultadoDTO = produtoService.createProduto(produtoDTO);
       
        assertEquals("Produto_1", resultadoDTO.nome());
        assertEquals(1.88F , resultadoDTO.preco(), 0);
        assertEquals(1L, resultadoDTO.quantidade());
        assertEquals("Lorem impsum", resultadoDTO.descricao());
        //assertEquals(1L , resultadoDTO.setorId());
        verify(produtoRepo, times(1)).save(any(Produto.class));
    }

    @Test
    void testCreateProdutoSetorNaoExiste() {
        
    }
    
}