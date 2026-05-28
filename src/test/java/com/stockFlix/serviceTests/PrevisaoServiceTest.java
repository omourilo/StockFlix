package com.stockFlix.serviceTests;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.stockFlix.movimentacao.MovimentacaoRepository;
import com.stockFlix.previsao.PrevisaoRepository;
import com.stockFlix.previsao.PrevisaoService;
import com.stockFlix.produto.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class PrevisaoServiceTest {
    
    @Mock
    private ProdutoRepository produtoRepo;
    @Mock
    private PrevisaoRepository previsaoRepo;
    @Mock
    private MovimentacaoRepository movimentacaoRepo;

    @InjectMocks
    private PrevisaoService previsaoService;


    

    
}
