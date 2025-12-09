// Represents a single bank account with account holder name, balance, and loan amount
public class Account {
	private String accountHolder; // Name of the account holder
	private double balance;       // Current account balance
	private double loan;          // Outstanding loan amount

	// Constructor to create a new account
	protected Account(String accountHolder, double balance) {
		this.accountHolder = accountHolder;
		this.balance = balance;
		this.loan = 0;
	}

	// Getter for the account holder's name
	protected String getAccountHolder() {
		return accountHolder;
	}

	// Getter for the account balance
	protected double getBalance() {
		return balance;
	}

	// Getter for the loan amount
	protected double getLoan() {
		return loan;
	}

	// Method to deposit money into the account
	protected void deposit(double amount) {
		balance += amount;
	}

	// Method to withdraw money from the account (only if balance is sufficient)
	protected boolean withdraw(double amount) {
		if (amount > balance) return false; // Insufficient funds
		balance -= amount;
		return true;
	}

	// Method to approve a loan for the account
	protected void approveLoan(double amount) {
		loan += amount;
	}

	// Method to repay a part of the loan (only if amount <= loan)
	protected boolean repayLoan(double amount) {
		if (amount > loan) return false; // Repayment exceeds loan
		loan -= amount;
		return true;
	}
}
