package com.stockFlix.movimentacao;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    
    @GetMapping("/{id}")
    public MovimentacaoDTO getMovimentacaoById(@PathVariable long id) {
    	return movimentacaoService.getMovimentacaoById(id);
    }
	
    @GetMapping
    public List<MovimentacaoDTO> getAllMovimentacoes() {
    	return movimentacaoService.getAllMovimentacoes();
    }
    
    @GetMapping("/data/{dataInicial}-{dataFinal}")
    public List<MovimentacaoDTO> getAllMovimentacoesByData(
    		@PathVariable String dataInicial, @PathVariable String dataFinal) {
    	return movimentacaoService.getAllMovimentacaoByData(dataInicial, dataFinal);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovimentacaoById(@PathVariable long id) {
    	movimentacaoService.deleteMovimentacao(id);
    }
}
