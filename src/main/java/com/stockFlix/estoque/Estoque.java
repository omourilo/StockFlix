package com.stockFlix.estoque;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stockFlix.setor.Setor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estoques")
public class Estoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	
	@Column(nullable = false)
	private String nome;

	@OneToMany(mappedBy = "estoque" , cascade = CascadeType.ALL)
	@JsonIgnore
	List<Setor> setores = new ArrayList<>();

	public Estoque(EstoqueDTO estoqueDTO) {
		this.nome = estoqueDTO.nome(); 
	}
	
	
}
