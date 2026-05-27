package com.stockFlix.serviceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.stockFlix.estoque.Estoque;
import com.stockFlix.excecoes.InsufficientStockException;
import com.stockFlix.excecoes.NotFoundException;
import com.stockFlix.movimentacao.Movimentacao;
import com.stockFlix.movimentacao.MovimentacaoDTO;
import com.stockFlix.movimentacao.MovimentacaoRepository;
import com.stockFlix.movimentacao.MovimentacaoService;
import com.stockFlix.produto.Produto;
import com.stockFlix.produto.ProdutoRepository;
import com.stockFlix.setor.Setor;
import com.stockFlix.usuario.Usuario;
import com.stockFlix.usuario.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class MovimentacaoServiceTest {
    
    @Mock
    private MovimentacaoRepository movimentacaoRepo;
    @Mock
    private ProdutoRepository produtoRepo;
    @Mock
    private UsuarioRepository usuarioRepo;

    @InjectMocks 
    private MovimentacaoService movimentacaoService;

    @Test
    void testCreateMovimentacaoAdicao() {

        Usuario usuarioEntity = new Usuario(1L, "Teste", "senha", true,  new ArrayList<>());

        Estoque estoqueEntity = new Estoque(1L, "Estoque_1" , new ArrayList<>()); 
        Setor setorEntity = new Setor(1L, "Setor_1", new ArrayList<>(), estoqueEntity);
        Produto produtoEntity = new Produto(1L, "Produto_1", 1.88F, 0, "Lorem impsum", setorEntity, new ArrayList<>(), new ArrayList<>());

        Movimentacao movimentEntity = new Movimentacao(1L, true, 10, LocalDate.of(2023, 10, 1), produtoEntity, usuarioEntity);

        MovimentacaoDTO movimentDTO = new MovimentacaoDTO(1L, true, 10, "2023-10-01", 1L, 1L); 

        when(usuarioRepo.findById(anyLong())).thenReturn(Optional.of(usuarioEntity));
        when(produtoRepo.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(movimentacaoRepo.save(any(Movimentacao.class))).thenReturn(movimentEntity);

        MovimentacaoDTO resultadoDTO = movimentacaoService.createMovimentacao(movimentDTO);

        assertEquals(1L, resultadoDTO.id());
        assertEquals(true, resultadoDTO.tipoMovimentacao());
        assertEquals(10, resultadoDTO.qtdMovimentada());
        assertEquals("2023-10-01", resultadoDTO.data());
        assertEquals(1L, resultadoDTO.produtoId());
        assertEquals(1L, resultadoDTO.usuarioId());
        assertEquals(10, produtoEntity.getQuantidade());
        verify(movimentacaoRepo, times(1)).save(any(Movimentacao.class));       

    }

    @Test
    void testCreateMovimentacaoSubtracao() {

        Usuario usuarioEntity = new Usuario(1L, "Teste", "senha", true,  new ArrayList<>());

        Estoque estoqueEntity = new Estoque(1L, "Estoque_1" , new ArrayList<>()); 
        Setor setorEntity = new Setor(1L, "Setor_1", new ArrayList<>(), estoqueEntity);
        Produto produtoEntity = new Produto(1L, "Produto_1", 1.88F, 10, "Lorem impsum", setorEntity, new ArrayList<>(), new ArrayList<>());

        Movimentacao movimentEntity = new Movimentacao(1L, false, 10, LocalDate.of(2023, 10, 1), produtoEntity, usuarioEntity);

        MovimentacaoDTO movimentDTO = new MovimentacaoDTO(1L, false, 10, "2023-10-01", 1L, 1L); 

        when(usuarioRepo.findById(anyLong())).thenReturn(Optional.of(usuarioEntity));
        when(produtoRepo.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(movimentacaoRepo.save(any(Movimentacao.class))).thenReturn(movimentEntity);

        MovimentacaoDTO resultadoDTO = movimentacaoService.createMovimentacao(movimentDTO);

        assertEquals(1L, resultadoDTO.id());
        assertEquals(false, resultadoDTO.tipoMovimentacao());
        assertEquals(10, resultadoDTO.qtdMovimentada());
        assertEquals("2023-10-01", resultadoDTO.data());
        assertEquals(1L, resultadoDTO.produtoId());
        assertEquals(1L, resultadoDTO.usuarioId());
        assertEquals(0, produtoEntity.getQuantidade());
        verify(movimentacaoRepo, times(1)).save(any(Movimentacao.class));       
        
    }

    @Test
    void testCreateMovimentacaoProdutoNaoExiste() {
        MovimentacaoDTO movimentDTO = new MovimentacaoDTO(1L, false, 10, "2023-10-01", 1L, 1L);

        when(produtoRepo.findById(anyLong())).thenReturn(Optional.empty()); 

        NotFoundException ex = assertThrows(NotFoundException.class, () -> movimentacaoService.createMovimentacao(movimentDTO));
        System.err.println(ex.getMessage());
    }

    @Test
    void testCreateMovimentacaoUsuarioNaoExiste() {
        Produto produtoEntity = new Produto(1L, "Produto_1", 1.88F, 10, "Lorem impsum", new Setor(), new ArrayList<>(), new ArrayList<>());
        MovimentacaoDTO movimentDTO = new MovimentacaoDTO(1L, false, 10, "2023-10-01", 1L, 1L);

        when(produtoRepo.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(usuarioRepo.findById(anyLong())).thenReturn(Optional.empty()); 

        NotFoundException ex = assertThrows(NotFoundException.class, () -> movimentacaoService.createMovimentacao(movimentDTO));
        System.err.println(ex.getMessage());
    }

    @Test
    void testCreateMovimentacaoEstoqueInsuficiente() {
        Usuario usuarioEntity = new Usuario(1L, "Teste", "senha", true,  new ArrayList<>());

        Estoque estoqueEntity = new Estoque(1L, "Estoque_1" , new ArrayList<>()); 
        Setor setorEntity = new Setor(1L, "Setor_1", new ArrayList<>(), estoqueEntity);
        Produto produtoEntity = new Produto(1L, "Produto_1", 1.88F, 0, "Lorem impsum", setorEntity, new ArrayList<>(), new ArrayList<>());

        MovimentacaoDTO movimentDTO = new MovimentacaoDTO(1L, false, 10, "2023-10-01", 1L, 1L); 

        when(usuarioRepo.findById(anyLong())).thenReturn(Optional.of(usuarioEntity));
        when(produtoRepo.findById(anyLong())).thenReturn(Optional.of(produtoEntity));

        InsufficientStockException ex = assertThrows(InsufficientStockException.class, () -> movimentacaoService.createMovimentacao(movimentDTO));
        System.err.println(ex.getMessage());
    }

    @Test
    void testDeleteMovimentacaoAdicao() {

        Produto produtoEntity = new Produto(1L, "Produto_1", 1.88F, 10, "Lorem impsum", new Setor(), new ArrayList<>(), new ArrayList<>());
        Movimentacao movimentEntity = new Movimentacao(1L, true, 10, LocalDate.of(2023, 10, 1), produtoEntity, new Usuario());

        when(produtoRepo.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(movimentacaoRepo.findById(anyLong())).thenReturn(Optional.of(movimentEntity));

        movimentacaoService.deleteMovimentacao(1L);

        assertEquals(0, produtoEntity.getQuantidade());
        verify(movimentacaoRepo, times(1)).delete(any(Movimentacao.class));
    }

    @Test
    void testDeleteMovimentacaoSubtracao() {

        Produto produtoEntity = new Produto(1L, "Produto_1", 1.88F, 0, "Lorem impsum", new Setor(), new ArrayList<>(), new ArrayList<>());
        Movimentacao movimentEntity = new Movimentacao(1L, false, 10, LocalDate.of(2023, 10, 1), produtoEntity, new Usuario());

        when(produtoRepo.findById(anyLong())).thenReturn(Optional.of(produtoEntity));
        when(movimentacaoRepo.findById(anyLong())).thenReturn(Optional.of(movimentEntity));

        movimentacaoService.deleteMovimentacao(1L);

        assertEquals(10, produtoEntity.getQuantidade());
        verify(movimentacaoRepo, times(1)).delete(any(Movimentacao.class));
    }

    @Test
    void testDeleteMovimentacaoNaoEncotrada() {

        when(movimentacaoRepo.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> movimentacaoService.deleteMovimentacao(1L));
        System.err.println(ex.getMessage());
    }

}
