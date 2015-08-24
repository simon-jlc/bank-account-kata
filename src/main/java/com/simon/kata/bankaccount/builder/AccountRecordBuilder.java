package com.simon.kata.bankaccount.builder;

import java.math.BigDecimal;
import java.util.Date;

import com.simon.kata.bankaccount.domain.AccountRecord;
import com.simon.kata.bankaccount.domain.AccountRecordId;
import com.simon.kata.bankaccount.domain.AccountRecordType;

/**
 * 
 * @since 23 août 2015
 * @author simon
 */
public class AccountRecordBuilder {

	private AccountRecord accountRecord;
	
	public AccountRecordBuilder() {
		accountRecord = new AccountRecord();
	}

	public AccountRecordBuilder createBuilder() {
		return this;
	}

	public AccountRecordBuilder id(AccountRecordId ident) {
		accountRecord.setAccountRecordId(ident);
		return this;
	}

	public AccountRecordBuilder amount(BigDecimal amount) {
		accountRecord.setAmount(amount);
		return this;
	}

	public AccountRecordBuilder recordType(AccountRecordType recordType) {
		accountRecord.setAccountRecordType(recordType);
		return this;
	}
	
	public AccountRecordBuilder date(Date date) {
		accountRecord.setDate(date);
		return this;
	}
	
	public AccountRecord build() {
		return accountRecord;
	}
}
