package com.simon.kata.bankaccount;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.simon.kata.bankaccount.builder.BankAccountBuilder;
import com.simon.kata.bankaccount.domain.AccountRecord;
import com.simon.kata.bankaccount.domain.AccountRecordType;
import com.simon.kata.bankaccount.domain.BankAccount;
import com.simon.kata.bankaccount.domain.BankAccountId;
import com.simon.kata.bankaccount.domain.CustomerId;
import com.simon.kata.bankaccount.query.AmountGreaterThanFilter;
import com.simon.kata.bankaccount.query.DateFilter;
import com.simon.kata.bankaccount.query.Filter;
import com.simon.kata.bankaccount.query.RecordTypeFilter;

/**
 * 
 * @since 23 août 2015
 * @author simon
 */
public class BankAccountServiceTest {

	private static final Object EMPTY_STATMENT = ""
			+ 	"BankAccountId:	FR001\n"
			+	"CustomerId:	1\n\n"
			+ 	"+-----------+------------+--------+--------------+\n"
			+	"| AccountID |    Date    |  TYPE  |    Amount    |\n"
			+	"+-----------+------------+--------+--------------+\n"
			+	"+-----------+------------+--------+--------------+";

	private String expectedStatement = ""
			+ 	"BankAccountId:	FR001\n"
			+	"CustomerId:	1\n\n"
			+ 	"+-----------+------------+--------+--------------+\n"
			+	"| AccountID |    Date    |  TYPE  |    Amount    |\n"
			+	"+-----------+------------+--------+--------------+\n"
			+	"|      1    | %s | CREDIT | 0001000,0000 |\n"
			+	"|      2    | %s |  DEBIT | 0001500,0000 |\n"
			+	"+-----------+------------+--------+--------------+";
	
	private String moreThan1000Statment = ""
			+ 	"BankAccountId:	FR001\n"
			+	"CustomerId:	1\n\n"
			+ 	"+-----------+------------+--------+--------------+\n"
			+	"| AccountID |    Date    |  TYPE  |    Amount    |\n"
			+	"+-----------+------------+--------+--------------+\n"
			+	"|      2    | %s |  DEBIT | 0001500,0000 |\n"
			+	"+-----------+------------+--------+--------------+";
	
	private BankAccountService bankAccountService;

	private BankAccount myAccount;

	private BankAccount anotherAccount;

	@Before
	public void setUp() {

		bankAccountService = new BankAccountService();

		myAccount = new BankAccountBuilder().createBuilder()
				.id(new BankAccountId("FR001")).customer(new CustomerId(1))
				.build();

		anotherAccount = new BankAccountBuilder().createBuilder()
				.id(new BankAccountId("FR002")).customer(new CustomerId(2))
				.build();
		
		// inject today date
		String today = AccountRecord.DD_MM_YYYY.format(new Date());
		expectedStatement = String.format(expectedStatement, today, today);
		moreThan1000Statment = String.format(moreThan1000Statment, today);
	}
	
	@Test
	public void shouldReturn0WhenNewBankAccount() {
		BigDecimal balance = bankAccountService.computeBalance(myAccount);
		assertThat(balance).isEqualTo(BigDecimal.valueOf(0.0));
	}
	
	@Test
	public void shouldHavePositiveBalanceWhenFirstDeposit() {
		BigDecimal balance = bankAccountService.deposit(myAccount, BigDecimal.valueOf(50));
		assertThat(balance).isPositive();
		assertThat(balance).isEqualTo(BigDecimal.valueOf(50.0));
	}
	
	@Test
	public void shouldHaveNegativeBalanceWhenFirstWithdraw() {
		BigDecimal balance = bankAccountService.withdraw(anotherAccount, BigDecimal.valueOf(50));
		assertThat(balance).isNegative();
		assertThat(balance).isEqualTo(BigDecimal.valueOf(-50.0));
	}
	
	@Test
	public void shouldHaveExactlyTheOppositAmmountWhenTransfer() {
		BigDecimal moneyTransferAmount = BigDecimal.valueOf(50.0);
		
		BigDecimal myAccountBalanceBefore = bankAccountService.computeBalance(myAccount);
		BigDecimal anotherAccountBalanceBefore = bankAccountService.computeBalance(anotherAccount);
		
		boolean transfer = bankAccountService.transfer(myAccount, anotherAccount, moneyTransferAmount);
		assertThat(transfer).isTrue();
		
		BigDecimal myAccountBalanceAfter = bankAccountService.computeBalance(myAccount);
		BigDecimal anotherAccountBalanceAfter = bankAccountService.computeBalance(anotherAccount);
		
		assertThat(myAccountBalanceAfter.subtract(myAccountBalanceBefore)).isEqualTo(moneyTransferAmount.negate());
		assertThat(anotherAccountBalanceAfter.subtract(anotherAccountBalanceBefore)).isEqualTo(moneyTransferAmount.negate());
		
	}
	
	@Test
	public void shouldPrintStatment() {
		generateMyAccountTransaction();
		String statment = bankAccountService.statment(myAccount);
		System.out.println(statment);
		assertThat(statment).isEqualTo(expectedStatement);
	}
	
	@Test
	public void shouldPrintEmptyStatmentByUnexpectedDate() throws ParseException {
		generateMyAccountTransaction();
		
		Filter dateFilter = new DateFilter(
				AccountRecord.DD_MM_YYYY.parse("01/01/2015"), 
				AccountRecord.DD_MM_YYYY.parse("02/01/2015"));
		
		String statment = bankAccountService.statment(myAccount, dateFilter);
		System.out.println(statment);
		assertThat(statment).isEqualTo(EMPTY_STATMENT);
	}

	@Test
	public void shouldPrintStatmentByFilteringDate() throws ParseException {
		generateMyAccountTransaction();
		Date today = new Date();
		Date fromLess5 = addDayToDate(today, -5);
		Date toPlus5 = addDayToDate(today, 5);
		
		Filter dateFilter = new DateFilter(
				fromLess5, 
				toPlus5);
		
		String statment = bankAccountService.statment(myAccount, dateFilter);
		System.out.println(statment);
		assertThat(statment).isEqualTo(expectedStatement);
	}
	
	@Test
	public void shouldPrintMoreThan1000AmountStatment() {
		generateMyAccountTransaction();
		Filter amountFilter = new AmountGreaterThanFilter(BigDecimal.valueOf(1000));
	
		String statment = bankAccountService.statment(myAccount, amountFilter);
		System.out.println(statment);
		assertThat(statment).isEqualTo(moreThan1000Statment);
	}
	
	@Test
	public void shouldPrintDebitStatment() {
		generateMyAccountTransaction();
		Filter amountFilter = new RecordTypeFilter(AccountRecordType.DEBIT);
	
		String statement = bankAccountService.statment(myAccount, amountFilter);
		System.out.println(statement);
		assertThat(statement).isEqualTo(moreThan1000Statment);
	}

	private void generateMyAccountTransaction() {
		bankAccountService.deposit(myAccount, BigDecimal.valueOf(1000));
		bankAccountService.withdraw(myAccount, BigDecimal.valueOf(1500));
	}
	
	private Date addDayToDate(Date date, int nbDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, nbDays);
		return cal.getTime();
	}
}
