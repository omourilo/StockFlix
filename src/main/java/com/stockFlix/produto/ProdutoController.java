package com.stockFlix.produto;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockFlix.setor.SetorService;

@RestController
@RequestMapping("/setores")
public class ProdutoController {
    
    private ProdutoService produtoService;
    private SetorService setorService;

    public ProdutoController(
        ProdutoService produtoService,
        SetorService setorService
    ) {
        this.produtoService = produtoService;
        this.setorService = setorService;
    }
}
