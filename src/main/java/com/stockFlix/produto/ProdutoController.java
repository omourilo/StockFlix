package com.stockFlix.produto;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stockFlix.setor.SetorService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    private ProdutoService produtoService;
    @SuppressWarnings("unused")
    private SetorService setorService;

    public ProdutoController(
        ProdutoService produtoService,
        SetorService setorService
    ) {
        this.produtoService = produtoService;
        this.setorService = setorService;
    }

    @GetMapping
    public List<ProdutoDTO> getAllProdutos() {
        return produtoService.readAllProdutos();
    }
    
    @GetMapping("/{id}")
    public ProdutoDTO getProdutoById(@PathVariable long id) {
        return produtoService.readProdutoById(id);
    }

    @GetMapping("/setor/{id}")
    public List<ProdutoDTO> getProdutoBySetorId(@PathVariable long id) {
        return produtoService.readAllProdutosBySetorId(id);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> createProduto(@RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.createProduto(produtoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> updateProduto(@PathVariable long id, @RequestBody ProdutoDTO produtoDTO) {
        return ResponseEntity.ok().body(produtoService.updateProduto(id, produtoDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduto(@PathVariable long id) {
        produtoService.deleteProduto(id);
    }
}
