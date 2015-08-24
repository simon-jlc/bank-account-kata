package com.simon.kata.bankaccount.query;

import com.simon.kata.bankaccount.domain.AccountRecordType;

/**
 * 
 * @since 24 août 2015
 * @author simon 
 */
public class RecordTypeFilter extends Filter {

	private AccountRecordType accountRecordType;	

	public RecordTypeFilter(AccountRecordType accountRecordType) {
		super();
		this.accountRecordType = accountRecordType;
		this.predicate = x -> accountRecordType.equals(x.getAccountRecordType());
	}
}
