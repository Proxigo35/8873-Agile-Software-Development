import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AccountTest {

	private Account account;

	@BeforeAll
	public static void beforeAll() {
		System.out.println("Starting Account tests.");
	}
	
	@AfterAll
	public static void afterAll() {
		System.out.println("Finished Account tests.");
	}

	@BeforeEach
	public void setUp() {
		account = new Account("Test Account", new BigDecimal("1000"));
	}

	@AfterEach
    public void tearDown() {
        account = null;
    }

	@ParameterizedTest
	@ValueSource(strings = {"-1000", "-1"})
	public void testAccountConstructorInvalid(String amount) {
		BigDecimal bdAmount = new BigDecimal(amount);
		assertThrows(
			IllegalArgumentException.class,
			() -> new Account("Test", bdAmount)
		);
	}

	@Test
	public void testGetAccountHolderValid() {
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
	@ValueSource(strings = {"10", "1000"})
	public void testDepositValid(String amount) {
		BigDecimal bdAmount = new BigDecimal(amount);
		assertTrue(account.deposit(bdAmount));
		assertEquals(new BigDecimal("1000").add(bdAmount), account.getBalance());
	}

	@ParameterizedTest
	@ValueSource(strings = {"-100", "-1", "0"})
	public void testDepositInvalidAmount(String amount) {
		BigDecimal bdAmount = new BigDecimal(amount);
		assertThrows(
			IllegalArgumentException.class,
			() -> account.deposit(bdAmount)
		);
		assertEquals(new BigDecimal("1000"), account.getBalance());
	}

	@ParameterizedTest
	@ValueSource(strings = {"10", "1000"})
	public void testWithdrawValid(String amount) {
		BigDecimal bdAmount = new BigDecimal(amount);
		assertTrue(account.withdraw(bdAmount));
		assertEquals(new BigDecimal("1000").subtract(bdAmount), account.getBalance());
	}

	@ParameterizedTest
	@ValueSource(strings = {"-100", "-1", "0"})
	public void testWithdrawInvalidAmount(String amount) {
		BigDecimal bdAmount = new BigDecimal(amount);
		assertThrows(
			IllegalArgumentException.class,
			() -> account.withdraw(bdAmount)
		);
		assertEquals(new BigDecimal("1000"), account.getBalance());
	}

	@ParameterizedTest
	@ValueSource(strings = {"1001", "2000"})
	public void testWithdrawInsufficientFunds(String amount) {
		assertFalse(account.withdraw(new BigDecimal(amount)));
		assertEquals(new BigDecimal("1000"), account.getBalance());
	}

	@ParameterizedTest
	@ValueSource(strings = {"100", "1000"})
	public void testApproveLoanValid(String amount) {
		BigDecimal bdAmount = new BigDecimal(amount);
		assertTrue(account.approveLoan(bdAmount));
		assertEquals(bdAmount, account.getLoan());
	}

	@ParameterizedTest
	@ValueSource(strings = {"-100", "-1", "0"})
	public void testApproveLoanInvalidAmount(String amount) {
		assertThrows(
			IllegalArgumentException.class,
			() -> account.approveLoan(new BigDecimal(amount))
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"10", "1000"})
	public void testRepayLoanValid(String amount) {
		BigDecimal bdAmount = new BigDecimal(amount);
		account.approveLoan(new BigDecimal("1000"));
		assertTrue(account.repayLoan(bdAmount));
		assertEquals(new BigDecimal("1000").subtract(bdAmount), account.getLoan());
	}

	@ParameterizedTest
	@ValueSource(strings = {"-100", "-1", "0"})
	public void testRepayLoanInvalidAmount(String amount) {
		BigDecimal bdAmount = new BigDecimal(amount);
		account.approveLoan(new BigDecimal("1000"));
		assertThrows(
			IllegalArgumentException.class,
			() -> account.repayLoan(bdAmount)
		);
		assertEquals(new BigDecimal("1000"), account.getLoan());
	}

	@ParameterizedTest
	@ValueSource(strings = {"1001", "2000"})
	public void testRepayLoanExceedingLoan(String amount) {
		BigDecimal bdAmount = new BigDecimal(amount);
		account.approveLoan(new BigDecimal("1000"));
		assertFalse(account.repayLoan(new BigDecimal(amount)));
		assertEquals(new BigDecimal("1000"), account.getLoan());
	}
}
