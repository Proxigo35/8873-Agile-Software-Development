import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.concurrent.TimeUnit;

public class BankingAppTest {

	private BankingApp bank;

	@BeforeAll
	public static void beforeAll() {
		System.out.println("Starting Banking App tests.");
	}

	@AfterAll
	public static void afterAll() {
		System.out.println("Finished Banking App tests.");
	}

	@BeforeEach
	public void setUp() {
		bank = new BankingApp();
		bank.addAccount("Alice", new BigDecimal("1000"));
	}

	@AfterEach
    public void tearDown() {
        bank = null;
    }

	@Test
	public void testFindAccountInvalid() {
		assertNull(bank.getBalance("Bob"));
	}

	@Test
	public void testAddAccount() {
		bank.addAccount("Bob", new BigDecimal("500"));
    	assertEquals(new BigDecimal("500"), bank.getBalance("Bob"));
		assertEquals(new BigDecimal("1500"), bank.getTotalDeposits());
	}

	@Test
	@Timeout(value = 1, unit = TimeUnit.SECONDS)
	public void testDeposit() {
		assertTrue(bank.deposit("Alice", new BigDecimal("1000")));
    	assertEquals(new BigDecimal("2000"), bank.getBalance("Alice"));
		assertEquals(new BigDecimal("2000"), bank.getTotalDeposits());
	}
	
	@Test
	public void testWithdrawValid() {
		assertTrue(bank.withdraw("Alice", new BigDecimal("300")));
    	assertEquals(new BigDecimal("700"), bank.getBalance("Alice"));
		assertEquals(new BigDecimal("700"), bank.getTotalDeposits());
	}

	@ParameterizedTest
	@ValueSource(strings = {"-100", "0", "1300"})
	public void testWithdrawInvalidAmounts(String amount) {
		assertFalse(bank.withdraw("Alice", new BigDecimal(amount)));
		assertFalse(bank.withdraw("Bob", new BigDecimal(amount)));
    	assertEquals(new BigDecimal("1000"), bank.getBalance("Alice"));
		assertEquals(new BigDecimal("1000"), bank.getTotalDeposits());
	}

	@Test
	public void testApproveLoanValid() {
		assertTrue(bank.approveLoan("Alice", new BigDecimal("1000")));
		assertEquals(new BigDecimal("1000"), bank.getLoan("Alice"));
		assertEquals(new BigDecimal("0"), bank.getTotalDeposits());
	}

	@Test
	public void testApproveLoanInsufficientFunds() {
		assertThrows(
			IllegalStateException.class,
			() -> bank.approveLoan("Alice", new BigDecimal("1500"))
		);
		assertEquals(new BigDecimal("0"), bank.getLoan("Alice"));
		assertEquals(new BigDecimal("1000"), bank.getTotalDeposits());
	}

	@Test
	public void testRepayLoanValid() {
		assertTrue(bank.approveLoan("Alice", new BigDecimal("200")));
		assertTrue(bank.repayLoan("Alice", new BigDecimal("100")));
		assertEquals(new BigDecimal("100"), bank.getLoan("Alice"));
		assertEquals(new BigDecimal("900"), bank.getTotalDeposits());
	}

	@ParameterizedTest
	@ValueSource(strings = {"-100", "0", "1300"})
	public void testRepayLoanInvalid(String amount) {
		assertTrue(bank.approveLoan("Alice", new BigDecimal("200")));
		assertFalse(bank.repayLoan("Alice", new BigDecimal(amount)));
		assertEquals(new BigDecimal("200"), bank.getLoan("Alice"));
		assertEquals(new BigDecimal("800"), bank.getTotalDeposits());
	}

	@Test
	public void testGetTotalDeposits() {
		assertEquals(new BigDecimal("1000"), bank.getTotalDeposits());
	}

	@Test
	public void testGetBalance() {
		assertEquals(bank.getBalance("Alice"), new BigDecimal("1000"));
		assertNull(bank.getBalance("Bob"));
	}

	@Test
	public void testGetLoan() {
		assertEquals(bank.getLoan("Alice"), new BigDecimal("0"));
		assertNull(bank.getLoan("Bob"));
	}
}
