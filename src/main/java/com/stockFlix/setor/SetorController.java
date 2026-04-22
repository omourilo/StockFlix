package com.stockFlix.setor;

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

import com.stockFlix.estoque.EstoqueService;

@RestController
@RequestMapping("/setores")
public class SetorController {
	
	private SetorService setorService;
	@SuppressWarnings("unused")
    private final EstoqueService estoqueService;
	
	
	public SetorController(
			SetorService setorService, 
			EstoqueService estoqueService
		) {
		this.setorService = setorService;
		this.estoqueService = estoqueService;
	}
	
    @GetMapping
    public List<SetorDTO> getAllSetores() {
        return setorService.readAllSetores(); 
    }
    
    @GetMapping("/{id}")
    public SetorDTO getSetorById(@PathVariable long id) {
        return setorService.readSetorById(id); 
    }
    
    @GetMapping("/estoque/{id}")
    public List<SetorDTO> getAllSetoresByEstoqueId(@PathVariable long id) {
    	return setorService.readSetoresByEstoqueId(id);
    }

    @PostMapping
    public ResponseEntity<SetorDTO> createSetor(@RequestBody SetorDTO setorDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(setorService.createSetor(setorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SetorDTO> updateSetor(@PathVariable long id, @RequestBody SetorDTO setorDTO) {
        return ResponseEntity.ok().body(setorService.updateSetor(id, setorDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) 
    public void deleteSetor(@PathVariable long id) {
        setorService.deleteSetor(id);
    }
}
