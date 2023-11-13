package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.unibo.bank.impl.SimpleBankAccount.*;
import static it.unibo.bank.impl.SimpleBankAccount.ATM_TRANSACTION_FEE;
import static it.unibo.bank.impl.StrictBankAccount.TRANSACTION_FEE;
import static org.junit.jupiter.api.Assertions.*;

class TestStrictBankAccount {

  private final static int INITIAL_AMOUNT = 100;

  // 1. Create a new AccountHolder and a StrictBankAccount for it each time tests
  // are executed.
  private AccountHolder mRossi;
  private BankAccount bankAccount;
  private static final int ID = 1;

  @BeforeEach
  public void setUp() {
    this.mRossi = new AccountHolder("Mario", "Rossi", ID);
    this.bankAccount = new StrictBankAccount(mRossi, 0);
  }

  // 2. Test the initial state of the StrictBankAccount
  @Test
  public void testInitialization() {
    Assertions.assertEquals(0, bankAccount.getBalance());
    Assertions.assertEquals(0, bankAccount.getTransactionsCount());
    Assertions.assertEquals(mRossi, bankAccount.getAccountHolder());
  }

  // 3. Perform a deposit of 100€, compute the management fees, and check that the
  // balance is correctly reduced.
  @Test
  public void testManagementFees() {
    bankAccount.deposit(ID, INITIAL_AMOUNT);
    assertEquals(100, bankAccount.getBalance());
    bankAccount.chargeManagementFees(ID); // FIXME what if there are no managment fees?
    assertTrue(this.bankAccount.getBalance() < INITIAL_AMOUNT);
  }

  // 4. Test the withdraw of a negative value
  @Test
  public void testNegativeWithdraw() {
    try {
      bankAccount.withdraw(ID, -1);
      fail();
    } catch (IllegalArgumentException e) {
      assertFalse(e.getMessage().isBlank());
    }
  }

  // 5. Test withdrawing more money than it is in the account
  @Test
  public void testWithdrawingTooMuch() {
    try {
      bankAccount.withdraw(ID, 9999);
      fail();
    } catch (IllegalArgumentException e) {
      assertFalse(e.getMessage().isBlank());
    }
  }
}
