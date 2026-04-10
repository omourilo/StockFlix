package com.stockFlix.estoque;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estoques")
public class EstoqueController {

    private EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public List<EstoqueDTO> getAllEstoques() {
        return estoqueService.readAllEstoques();
    }
    
    @GetMapping("/{id}")
    public EstoqueDTO getEstoqueById(@PathVariable long id) {
        return estoqueService.findEstoqueById(id);
    }

    @PostMapping
    public ResponseEntity<EstoqueDTO> createEstoque(@RequestBody EstoqueDTO estoqueDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estoqueService.createEstoque(estoqueDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueDTO> updateEstoque(@PathVariable long id, @RequestBody EstoqueDTO estoqueDTO) {
        return ResponseEntity.ok().body(estoqueService.updateEstoque(id, estoqueDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void deleteEstoque(@PathVariable long id) {
        estoqueService.deleteEstoque(id);
    }
}