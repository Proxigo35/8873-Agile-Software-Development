import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.BeforeEach;

public class AccountTest {

	private Account account;

	@BeforeEach
	void setUp() {
		account = new Account("Test Account", new BigDecimal("1000"));
	}

	@Test
	public void testGetAccountHolder() {
		assertEquals("Test Account", account.getAccountHolder());
	}

	@Test
	public void testGetBalance() {
		assertEquals(new BigDecimal("1000"), account.getBalance());
	}

	@Test
	public void testGetLoan() {
		assertEquals(BigDecimal.ZERO, account.getLoan());
	}

	@ParameterizedTest
	@ValueSource(strings = {"10", "500", "1000"})
	public void testDeposit(BigDecimal amount) {
		account.deposit(amount);
		assertEquals(new BigDecimal("1000").add(amount), account.getBalance());
	}

	@ParameterizedTest
	@ValueSource(strings = {"-1000", "-1", "0"})
	public void testDepositIllegalArguments(BigDecimal amount) {
		assertThrows(
			IllegalArgumentException.class,
			() -> account.deposit(amount)
		);
		assertEquals(new BigDecimal("1000"), account.getBalance());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"-1000", "-10", "0", "10", "1000"})
	public void testWithdraw(BigDecimal amount) {
		if (account.withdraw(amount)) assertEquals(new BigDecimal("1000").subtract(amount), account.getBalance());
		else assertEquals(BigDecimal.ZERO, account.getBalance());
	}


	@ParameterizedTest
	@ValueSource(strings = {"-1000", "-10", "0", "10", "1000"})
	public void testApproveLoan(BigDecimal amount) {
		try {
			account.approveLoan(amount);
			assertEquals(amount, account.getLoan());
		} catch(IllegalArgumentException e) {
			assertEquals(BigDecimal.ZERO, account.getLoan());
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {"-1000", "0", "1000"})
	public void testRepayLoan(BigDecimal amount) {
		account.approveLoan(new BigDecimal("2000"));
		if (account.repayLoan(amount)) assertEquals(new BigDecimal("2000").subtract(amount), account.getLoan());
		else assertEquals(new BigDecimal("2000"), account.getLoan());
	}
}
