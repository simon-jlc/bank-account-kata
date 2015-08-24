package com.simon.kata.bankaccount.query;

import java.math.BigDecimal;

/**
 * 
 * @since 23 août 2015
 * @author simon 
 */
public class AmountGreaterThanFilter extends Filter {

	private BigDecimal amount;

	public AmountGreaterThanFilter(BigDecimal amount) {
		super();
		this.amount = amount;
		
		this.predicate = x -> x.getAmount().compareTo(amount) > 0; 
	}
}
