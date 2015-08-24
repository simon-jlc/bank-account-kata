package com.simon.kata.bankaccount.builder;

import java.util.List;

import com.simon.kata.bankaccount.domain.AccountRecord;
import com.simon.kata.bankaccount.domain.BankAccount;
import com.simon.kata.bankaccount.domain.BankAccountId;
import com.simon.kata.bankaccount.domain.CustomerId;

/**
 * 
 * @since 23 août 2015
 * @author simon 
 */
public class BankAccountBuilder {
	
	private BankAccount bankAccount;

	public BankAccountBuilder() {
		bankAccount = new BankAccount();
	}
	
	public BankAccountBuilder createBuilder() {
		return this;
	}
	
	public BankAccountBuilder id(BankAccountId ident) {
		bankAccount.setBankAccountId(ident);
		return this;
	}
	
	public BankAccountBuilder customer(CustomerId ident) {
		bankAccount.setCustomerId(ident);
		return this;
	}
	
	public BankAccountBuilder records(List<AccountRecord> records) {
		bankAccount.setRecords(records);
		return this;
	}
	
	public BankAccountBuilder record(AccountRecord record) {
		bankAccount.getRecords().add(record);
		return this;
	}
	
	public BankAccount build() {
		return bankAccount;
	}
	
}
