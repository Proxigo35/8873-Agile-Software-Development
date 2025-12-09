import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

	Account account = new Account("Bob", 1000);

	@Test
	public void testGetAccountHolder() {
		assertEquals("Bob", account.getAccountHolder());
	}

	@Test
	public void testGetBalance() {

	}

	@Test
	public void testGetLoan() {

	}

	@Test
	public void testDeposit() {

	}

	@Test
	public void testWithdraw() {

	}

	@Test
	public void testApproveLoan() {

	}

	@Test
	public void testRepayLoan() {

	}
}
