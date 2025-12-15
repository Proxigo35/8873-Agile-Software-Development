import java.math.BigDecimal;

// Represents a single bank account with account holder name, balance, and loan amount
public class Account {
	private String accountHolder; // Name of the account holder
	private BigDecimal balance;       // Current account balance
	private BigDecimal loan;          // Outstanding loan amount

	// Constructor to create a new account
	protected Account(String accountHolder, BigDecimal balance) {
		if (balance.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Initial balance cannot be negative.");
		
		this.accountHolder = accountHolder;
		this.balance = balance;
		this.loan = BigDecimal.ZERO;
	}

	// Getter for the account holder's name
	protected String getAccountHolder() {
		return accountHolder;
	}

	// Getter for the account balance
	protected BigDecimal getBalance() {
		return balance;
	}

	// Getter for the loan amount
	protected BigDecimal getLoan() {
		return loan;
	}

	// Method to deposit money into the account
	protected void deposit(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Deposit amount must be greater than 0.");
		balance = balance.add(amount);
	}

	// Method to withdraw money from the account (only if balance is sufficient)
	protected boolean withdraw(BigDecimal amount) {
		if (amount.compareTo(balance) > 1) return false; // Insufficient funds
		balance = balance.subtract(amount);
		return true;
	}

	// Method to approve a loan for the account
	protected void approveLoan(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 1) throw new IllegalArgumentException("Loan amount must be greater than 0.");
		loan = loan.add(amount);
	}

	// Method to repay a part of the loan (only if amount <= loan)
	protected boolean repayLoan(BigDecimal amount) {
		if (amount.compareTo(loan) >= 0) return false; // Repayment exceeds loan
		loan = loan.subtract(amount);
		return true;
	}
}
