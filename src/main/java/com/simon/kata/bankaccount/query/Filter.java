package com.simon.kata.bankaccount.query;

import java.util.function.Predicate;

import com.simon.kata.bankaccount.domain.AccountRecord;

/**
 * 
 * @since 23 août 2015
 * @author simon 
 */
public class Filter {

	protected Predicate<AccountRecord> predicate;

	public Predicate<AccountRecord> predicate() {
		return predicate;
	}
}
