package com.abc;

import com.abc.account.Account;
import com.abc.account.impl.Checking;
import com.abc.account.impl.MaxiSavings;
import com.abc.account.impl.Savings;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vlad Kostiuchenko on 7/24/16.
 * E-mail: sadkou@gmail.com
 * cell: 6467063785
 */

public class CustomerTest {

    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Checking();
        Account savingsAccount = new Savings();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                        "\n" +
                        "Checking\n" +
                        "deposit Date: "+ LocalDate.now().format(DATE_FORMATTER) +", Amount: $100.00\n" +
                        "Total: $100.00\n" +
                        "\n" +
                        "Savings\n" +
                        "deposit Date: "+ LocalDate.now().format(DATE_FORMATTER) +", Amount: $4,000.00\n" +
                        "withdrawal Date: "+ LocalDate.now().format(DATE_FORMATTER) +", Amount: ($200.00)\n" +
                        "Total: $3,800.00\n" +
                        "\n" +
                        "Total In All Accounts $3,900.00", henry.getStatement());

//        assertEquals("Statement for Henry\n" +
//                "\n" +
//                "Checking Account\n" +
//                "  deposit $100.00\n" +
//                "Total $100.00\n" +
//                "\n" +
//                "Savings Account\n" +
//                "  deposit $4,000.00\n" +
//                "  withdrawal $200.00\n" +
//                "Total $3,800.00\n" +
//                "\n" +
//                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Savings());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Savings());
        oscar.openAccount(new Checking());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Checking())
                .openAccount(new Savings())
                .openAccount(new MaxiSavings());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
