package com.simon.kata.bankaccount.query;

import java.util.Date;

/**
 * 
 * @since 23 août 2015
 * @author simon 
 */
public class DateFilter extends Filter {

	private Date from;
	private Date to;
	
	public DateFilter(Date from, Date to) {
		super();
		this.from = from;
		this.to = to;
		
		this.predicate = x -> x.getDate().compareTo(from) > 0 
				&& x.getDate().compareTo(to) < 0; 
	}
}
