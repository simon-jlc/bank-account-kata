package com.simon.kata.bankaccount;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.simon.kata.bankaccount.builder.AccountRecordBuilder;
import com.simon.kata.bankaccount.domain.AccountRecord;
import com.simon.kata.bankaccount.domain.AccountRecordId;
import com.simon.kata.bankaccount.domain.AccountRecordType;
import com.simon.kata.bankaccount.domain.BankAccount;
import com.simon.kata.bankaccount.query.Filter;

/**
 * 
 * @since 23 août 2015
 * @author simon 
 */
public class BankAccountService {

	public BigDecimal computeBalance(BankAccount myAccount) {

		double debits = myAccount.getRecords()
			.stream()
			.filter(rec -> AccountRecordType.DEBIT.equals(rec.getAccountRecordType()))
			.mapToDouble(rec -> rec.getAmount().doubleValue())
			.sum();
		
		double credits = myAccount.getRecords()
				.stream()
				.filter(rec -> AccountRecordType.CREDIT.equals(rec.getAccountRecordType()))
				.mapToDouble(rec -> rec.getAmount().doubleValue())
				.sum();
		
		return BigDecimal.valueOf(credits - debits);
	}
	
	public BigDecimal deposit(BankAccount bankAccount, BigDecimal amount) {
		return recordTransaction(bankAccount, amount, AccountRecordType.CREDIT);
	}
	
	public BigDecimal withdraw(BankAccount bankAccount, BigDecimal amount) {
		return recordTransaction(bankAccount, amount, AccountRecordType.DEBIT);
	}
	
	public boolean transfer(BankAccount fromBankAccount, BankAccount toBankAccount, BigDecimal amount) {
		
		try {
			BigDecimal withdrawFrom = withdraw(fromBankAccount, amount);
			deposit(toBankAccount, withdrawFrom);			
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public String statment(BankAccount bankAccount) {
		return bankAccount.print(Optional.empty());
	}
	
	public String statment(BankAccount bankAccount, Filter...filter) {
		List<Predicate<AccountRecord>> filters = Arrays.asList(filter).stream().map(f -> f.predicate()).collect(Collectors.toList());
		Predicate<AccountRecord> mergeFilters = filters.stream().reduce(Predicate::and).orElse(x->true);
		List<AccountRecord> collect = bankAccount.getRecords().stream().filter(mergeFilters).collect(Collectors.toList());
		return bankAccount.print(Optional.of(collect));
	}
	
	private BigDecimal recordTransaction(BankAccount bankAccount,
			BigDecimal amount, AccountRecordType accountRecordType) {
		if(bankAccount == null) {
			throw new BankAccountException("Technical exception: Bank account is not initialized");
		}
		
		AccountRecord accountRecord = new AccountRecordBuilder()
			.createBuilder()
			.id(new AccountRecordId(String.valueOf(bankAccount.getRecords().size() + 1)))
			.amount(amount)
			.recordType(accountRecordType)
			.date(new Date())
			.build();
		
		bankAccount.getRecords().add(accountRecord);
		
		return computeBalance(bankAccount);
	}
}
