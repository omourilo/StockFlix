package com.stockFlix.movimentacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockFlix.produto.ProdutoService;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {
	
	private MovimentacaoService movimentacaoService;
	@SuppressWarnings("unused")
	private ProdutoService produtoService;
	
	
	public MovimentacaoController(
			MovimentacaoService movimentacaoService, 
			ProdutoService produtoService) {
		this.movimentacaoService = movimentacaoService;
		this.produtoService = produtoService;
	}
	
    @PostMapping
    public ResponseEntity<MovimentacaoDTO> createMovimentacao(@RequestBody MovimentacaoDTO movimentacaoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
        		.body(movimentacaoService.createMovimentacao(movimentacaoDTO));
    }
    
	
}
