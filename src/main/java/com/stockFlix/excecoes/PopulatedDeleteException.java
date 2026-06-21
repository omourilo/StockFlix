package com.stockFlix.excecoes;

public class PopulatedDeleteException extends RuntimeException{
    private static final long serialVersionUID = 1L;
	
	public PopulatedDeleteException(String warning) {
		super(warning);
	}
}
