package com.simon.kata.bankaccount.domain;

/**
 * 
 * @since 23 août 2015
 * @author simon 
 */
public class CustomerId {
	
	private Integer ident;

	public CustomerId(Integer ident) {
		super();
		this.ident = ident;
	}

	@Override
	public String toString() {
		return ident.toString();
	}
}
