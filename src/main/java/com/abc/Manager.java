package com.abc;

import java.util.stream.Collectors;

/**
 * Created by Vlad Kostiuchenko on 7/24/16.
 * E-mail: sadkou@gmail.com
 * cell: 6467063785
 */

public class Manager {

    public String getClients() {
        return "List of customers:\n" + Bank.getInstance().getCustomers().stream().map(c -> "Customer name: " + c.getName() + ", Number of acct: " + c.getNumberOfAccounts()).collect(Collectors.joining("\n"));
    }

    public double getTotalInterest() {
        return Bank.getInstance().getCustomers().stream().mapToDouble(c -> c.totalInterestEarned()).sum();
    }
}
