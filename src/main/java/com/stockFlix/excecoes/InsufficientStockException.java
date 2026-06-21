package com.stockFlix.excecoes;

public class InsufficientStockException extends RuntimeException{
    private static final long serialVersionUID = 1L;
	
	public InsufficientStockException(String warning) {
		super(warning);
	}
}
