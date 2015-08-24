package com.simon.kata.bankaccount.domain;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @since 23 août 2015
 * @author simon
 */
public class AccountRecord {

	public static final DateFormat DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");
	private static final NumberFormat DDDD_DDDD = new DecimalFormat("0000000.0000"); 
	
	private static final String NA = "NA";
	private static final String ROW = "| %s    | %s | %s | %s |\n";
	protected AccountRecordId accountRecordId;
	protected BigDecimal amount;
	protected AccountRecordType accountRecordType;
	private Date date;

	public AccountRecord() {
		super();
	}

	public AccountRecord(AccountRecordId accountRecord, BigDecimal amount,
			AccountRecordType accountRecordType, Date date) {
		super();
		this.accountRecordId = accountRecord;
		this.amount = amount;
		this.accountRecordType = accountRecordType;
		this.date = date;
	}

	public void print(StringBuilder sb) {
		String accountRecordIdStr = accountRecordId == null ? NA : accountRecordId.toString();
		String dateStr = date == null ? NA : DD_MM_YYYY.format(date);
		String typeStr = accountRecordType == null ? NA : accountRecordType.toString();
		String amountStr = amount == null ? NA : DDDD_DDDD.format(amount);

		sb.append(String.format(ROW, 
				StringUtils.leftPad(accountRecordIdStr, 6, ' '), 
				dateStr,
				StringUtils.leftPad(typeStr, 6, ' '), 
				amountStr));
	}

	/*************************/
	/*** GETTERs & SETTERs ***/
	/*************************/
	public AccountRecordId getAccountRecordId() {
		return accountRecordId;
	}

	public void setAccountRecordId(AccountRecordId accountRecordId) {
		this.accountRecordId = accountRecordId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public AccountRecordType getAccountRecordType() {
		return accountRecordType;
	}

	public void setAccountRecordType(AccountRecordType accountRecordType) {
		this.accountRecordType = accountRecordType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
