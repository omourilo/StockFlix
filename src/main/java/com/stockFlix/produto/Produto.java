package com.stockFlix.produto;

import java.util.List;
import java.util.ArrayList; 

import com.stockFlix.previsao.Previsao;
import com.stockFlix.setor.Setor;
import com.stockFlix.excecoes.InsufficientStockException;
import com.stockFlix.movimentacao.Movimentacao;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
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

	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	@JsonIgnore 
	List<Previsao> previsoes = new ArrayList<>();
	
	public Produto(ProdutoDTO produtoDTO) {
		this.nome = produtoDTO.nome();
		this.preco = produtoDTO.preco();
		//deixar sem quantidade para iniciar o produto com 0 
		this.descricao = produtoDTO.descricao();
	}

	public int adicionarQuantidade(int qtdAdicionada) {
		int soma = this.quantidade + qtdAdicionada;
		setQuantidade(soma);
		return soma;	
	}

	public int removerQuantidade(int qtdRemovida) {
		if (this.quantidade < qtdRemovida) {
			throw new InsufficientStockException("Resultado da operação não pode ser negativo!!!");
		}
		int diff = this.quantidade - qtdRemovida;
		setQuantidade(diff);
		return diff;
	}
}
