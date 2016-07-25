package com.abc;

import com.abc.account.Account;
import com.abc.account.impl.Checking;
import com.abc.account.impl.MaxiSavings;
import com.abc.account.impl.Savings;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vlad Kostiuchenko on 7/24/16.
 * E-mail: sadkou@gmail.com
 * cell: 6467063785
 */

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = Bank.getInstance();
        Customer john = new Customer("John");
        john.openAccount(new Checking());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
        Bank.cleanInstance();
    }

    @Test
    public void checkingAccount() {
        Bank bank = Bank.getInstance();
        Account checkingAccount = new Checking();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.addTransaction(new Transaction(100, LocalDate.now().minusDays(30)));
        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
        Bank.cleanInstance();
    }

    @Test
    public void savings_account() {
        Bank bank = Bank.getInstance();
        Account savingsAccount = new Savings();
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.addTransaction(new Transaction(1100, LocalDate.now().minusDays(30)));
        assertEquals(1.2, bank.totalInterestPaid(), DOUBLE_DELTA);
        Bank.cleanInstance();
    }

    @Test
    public void maxi_savings_account() {
//        Bank bank = new Bank();
        Bank bank = Bank.getInstance();
        Account savingsAccount = new MaxiSavings();
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.addTransaction(new Transaction(1000, LocalDate.now().minusDays(30)));
        assertEquals(50.0, bank.totalInterestPaid(), DOUBLE_DELTA);

        savingsAccount.addTransaction(new Transaction(-500, LocalDate.now().minusDays(15)));
        assertEquals(37.5, bank.totalInterestPaid(), DOUBLE_DELTA);

        savingsAccount.addTransaction(new Transaction(-250, LocalDate.now().minusDays(7)));
        assertEquals(25.191666666666666, bank.totalInterestPaid(), DOUBLE_DELTA);
        Bank.cleanInstance();
    }

}
