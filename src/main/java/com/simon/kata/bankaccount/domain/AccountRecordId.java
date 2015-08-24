package com.simon.kata.bankaccount.domain;

/**
 * 
 * @since 23 août 2015
 * @author simon 
 */
public class AccountRecordId {

	private String ident;

	public AccountRecordId(String ident) {
		super();
		this.ident = ident;
	}

	@Override
	public String toString() {
		return ident;
	}
}
