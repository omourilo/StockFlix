package com.stockFlix.movimentacao;

import java.time.LocalDate;

import com.stockFlix.produto.Produto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimentacoes")
public class Movimentacao {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Define se é uma movimentação de saída ou entrada.
     */
    @Column(nullable = false)
    private Boolean tipoMovimentacao;

    @Column(nullable = false)
    @Min(0)
    private int qtdMovimentada; 

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto; 


    public Movimentacao(MovimentacaoDTO movimentacaoDTO) {
        this.tipoMovimentacao = movimentacaoDTO.tipoMovimentacao();
        this.qtdMovimentada = movimentacaoDTO.qtdMovimentada();
        this.data = LocalDate.parse(movimentacaoDTO.data());
        
    }
}