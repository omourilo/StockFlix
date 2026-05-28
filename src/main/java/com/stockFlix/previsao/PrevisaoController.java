package com.stockFlix.previsao;

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

@RestController
@RequestMapping("/previsoes")
public class PrevisaoController {
    
    private PrevisaoService previsaoService;


    public PrevisaoController(
            PrevisaoService previsaoService) {
        this.previsaoService = previsaoService;
    }

    @PostMapping
    public ResponseEntity<PrevisaoDTO> createPrevisao(@RequestBody PrevisaoDTO previsaoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(previsaoService.createPrevisao(previsaoDTO));
    }

    @GetMapping("/{id}")
    public PrevisaoDTO getPrevisaoById(@PathVariable long id) {
    	return previsaoService.getPrevisaoById(id);
    }

    @GetMapping
    public List<PrevisaoDTO> getAllPrevisoes() {
    	return previsaoService.getAllPrevisoes();
    }
    
    @GetMapping("/produto/{id}")
    public List<PrevisaoDTO> getAllPrevisoesByProdutoId(@PathVariable long id) {
        return previsaoService.getAllPrevisoesByProdutoId(id);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrevisaoById(@PathVariable long id) {
    	previsaoService.deletePrevisao(id);
    }
    
   
}
