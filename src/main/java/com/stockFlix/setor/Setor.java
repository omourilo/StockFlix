package com.stockFlix.setor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stockFlix.estoque.Estoque;
import com.stockFlix.produto.Produto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "setores")
public class Setor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	
	@Column(nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "setor", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Produto> produtos = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "estoque_id", nullable = false)
	private Estoque estoque;

	public Setor(SetorDTO setorDTO) {
		this.nome = setorDTO.nome();
	}
	

}
