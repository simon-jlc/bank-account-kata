package com.simon.kata.bankaccount.domain;

/**
 * 
 * @since 23 août 2015
 * @author simon 
 */
public class BankAccountId {
	private String ident;

	public BankAccountId(String ident) {
		super();
		this.ident = ident;
	}

	@Override
	public String toString() {
		return ident;
	}
}
