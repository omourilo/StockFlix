package com.stockFlix.Excecoes;


/**
 * Classe para lançamento de exceção para busca sem resultado.
 * 
 * <p>Caso alguma busca no banco não encontre resultado, 
 * temos essa exceção específica. 
 * Facilitando o tratamento de erros e o teste unitario.</p>
 */
public class NotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public NotFoundException(String warning) {
		super(warning);
	}

}
