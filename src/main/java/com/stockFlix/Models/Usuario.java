package com.stockFlix.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String login;
	@Column(nullable = false)
	private String senha;
	
	/**
	 * parametro para decidir nivel de acesso do usuario, 
	 * true para acesso ADM e false para acesso comum
	 */
	@Column(nullable = false)
	private Boolean acessoADM;
	
	

}
