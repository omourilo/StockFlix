package com.stockFlix.produto;

import java.util.List;

import com.stockFlix.previsao.Previsao;
import com.stockFlix.setor.Setor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	@Min(0)
	private float preco;
	
	@Column(nullable = false)
	@Min(0)
	private int quantidade;
	
	@Column
	@Size(max = 300)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "setor_id")
	private Setor setor;
	
	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	@JsonIgnore
	List<Movimentacao> movimentacoes = new ArrayList<>();

	@OneToMany(mappedBy = "produto", cascade = CascadeType.All)
	@JsonIgnore 
	List<Previsao> previsoes = new ArrayList<>();
	
}
