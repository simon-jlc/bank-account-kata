package com.simon.kata.bankaccount.builder;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.simon.kata.bankaccount.domain.AccountRecord;
import com.simon.kata.bankaccount.domain.AccountRecordId;
import com.simon.kata.bankaccount.domain.AccountRecordType;


/**
 * 
 * @since 23 août 2015
 * @author simon 
 */
public class AccountRecordBuilderTest {

	private AccountRecord accountRecord;

	@Before
	public void setUp() {
		
		accountRecord = new AccountRecordBuilder()
			.createBuilder()
			.id(new AccountRecordId("123"))
			.amount(BigDecimal.valueOf(1500))
			.recordType(AccountRecordType.DEBIT).build();
		
	}
	
	@Test
	public void test() {
		System.out.println(accountRecord);
	}
}
