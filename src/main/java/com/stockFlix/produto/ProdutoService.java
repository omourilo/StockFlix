package com.stockFlix.produto;

import org.springframework.stereotype.Service;

import com.stockFlix.setor.SetorRepository;

@Service
public class ProdutoService {
    
    private final ProdutoRepository produtoRepo;
    private final SetorRepository setorRepo; 

    public ProdutoService(
        ProdutoRepository produtoRepo,
        SetorRepository setorRepo
    ) { 
        this.produtoRepo = produtoRepo;
        this.setorRepo = setorRepo;
    }


}
