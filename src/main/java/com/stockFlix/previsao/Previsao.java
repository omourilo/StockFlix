package com.stockFlix.previsao;

import java.time.LocalDate;

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
@Table(name = "previsoes")
public class Previsao {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(0)
    private int qtdPrevista;

    @Column(nullable = false)
    private LocalDate inicioPeriodo;

    @Column(nullable = false)
    private LocalDate fimPeriodo;

    @Column
    private LocalDate criadoEm = LocalDate.now(); 

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    
}