package com.stockFlix.serviceTests;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
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

        


    }
}
