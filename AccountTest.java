import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

	Account account = new Account("Bob", 1000);

	@Test
	public void testGetAccountHolder() {
		assertEquals("Bob", account.getAccountHolder());
	}

	@Test
	public void testGetBalance() {
		assertEquals(1000, account.getBalance());
	}

	@Test
	public void testGetLoan() {
		assertEquals(0, account.getLoan());
	}

	// Need to properly handle negative numbers and the account overflowing by refactoring code
	@ParameterizedTest
	@ValueSource(doubles = {-500, 0, 500, Double.MAX_VALUE, Double.MAX_VALUE})
	public void testDeposit(double depositAmount) {
		double balanceBefore = account.getBalance();
		account.deposit(depositAmount);
		assertEquals(balanceBefore + depositAmount, account.getBalance());
	}

	// Need to properly handle negative numbers by refactoring code
	@ParameterizedTest
	@ValueSource(doubles = {-500, 0, 500, Double.MAX_VALUE, Double.MAX_VALUE})
	public void testWithdraw(double withdrawAmount) {
		double balanceBefore = account.getBalance();
		if (account.withdraw(withdrawAmount)) {
			assertEquals(balanceBefore - withdrawAmount, account.getBalance());
		}
	}

	@Test
	public void testApproveLoan() {
		account.approveLoan(1000);
		assertEquals(1000, account.getLoan());
	}

	// Need to properly handle negative numbers by refactoring code
	@ParameterizedTest
	@ValueSource(doubles = {-500, 0, 500, Double.MAX_VALUE, Double.MAX_VALUE})
	public void testRepayLoan(double repayAmount) {
		double loanBefore = account.getLoan();
		if (account.repayLoan(repayAmount)) {
			assertEquals(loanBefore - repayAmount, account.getLoan());
		}
	}
}
