package com.abc;

import com.abc.account.Account;
import com.abc.account.impl.Checking;
import com.abc.account.impl.MaxiSavings;
import com.abc.account.impl.Savings;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vlad Kostiuchenko on 7/24/16.
 * E-mail: sadkou@gmail.com
 * cell: 6467063785
 */

public class ManagerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testGetClients() {
        Bank bank = Bank.getInstance();
        bank.addCustomer(new Customer("John").openAccount(new Checking()).openAccount(new Savings()))
                .addCustomer(new Customer("Eric").openAccount(new Checking()).openAccount(new Savings()).openAccount(new MaxiSavings()));

        assertEquals(bank.getBankManager().getClients(), "List of customers:\nCustomer name: John, Number of acct: 2\nCustomer name: Eric, Number of acct: 3");
        Bank.cleanInstance();
    }



    @Test
    public void testGetTotalInterest() {
        Bank bank = Bank.getInstance();
        Account checkingAccount = new Checking();
        Account savingsAccount = new Savings();
        Account maxiSavings = new MaxiSavings();

        Account checkingAccount2 = new Checking();
        Account savingsAccount2 = new Savings();

        bank.addCustomer(new Customer("John").openAccount(checkingAccount2).openAccount(savingsAccount2))
                .addCustomer(new Customer("Eric").openAccount(checkingAccount).openAccount(savingsAccount).openAccount(maxiSavings));

        savingsAccount.addTransaction(new Transaction(1100, LocalDate.now().minusDays(30)));

        checkingAccount.addTransaction(new Transaction(100, LocalDate.now().minusDays(30)));

        maxiSavings.addTransaction(new Transaction(1000, LocalDate.now().minusDays(30)));
        maxiSavings.addTransaction(new Transaction(-500, LocalDate.now().minusDays(15)));
        maxiSavings.addTransaction(new Transaction(-250, LocalDate.now().minusDays(7)));

        checkingAccount2.addTransaction(new Transaction(1500, LocalDate.now().minusDays(30)));
        savingsAccount2.addTransaction(new Transaction(1100, LocalDate.now().minusDays(30)));

        assertEquals(29.191666666666666, bank.getBankManager().getTotalInterest(), DOUBLE_DELTA);
        Bank.cleanInstance();
    }

}
