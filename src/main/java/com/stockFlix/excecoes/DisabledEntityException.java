package com.stockFlix.excecoes;

public class DisabledEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DisabledEntityException(String warning) {
		super(warning);
	}
	
	
}
