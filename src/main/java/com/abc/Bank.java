package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Vlad Kostiuchenko on 7/24/16.
 * E-mail: sadkou@gmail.com
 * cell: 6467063785
 */

public class Bank {

    private static volatile Bank instance;
    private static final List<Customer> customers = new ArrayList<>();
    private static Manager bankManager = new Manager();

    //For testing
    public static void cleanInstance() {
        instance = null;
        customers.clear();
    }
    private Bank() {}

    public Bank addCustomer(Customer customer) {
        if (customers.contains(customer)) System.out.println("We already have this customer");
        else customers.add(customer);
        return this;
    }

    public String customerSummary() {

        Function<Customer, String> func = c -> {
            String tmp = "";
            if (c.getNumberOfAccounts() > 1) tmp ="s";
            return "\n - " + c.getName() + " (" + c.getNumberOfAccounts() + " account" + tmp + ")";
        };
        return "Customer Summary" + customers.parallelStream().map(func).collect(Collectors.joining());
    }

    public double totalInterestPaid() {
        return customers.parallelStream().mapToDouble(Customer::totalInterestEarned).sum();
    }

    public static Bank getInstance() {
        if (instance == null)
            synchronized (Bank.class) {
                if (instance == null) instance = new Bank();
            }

        return instance;
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    public Manager getBankManager() {
        return bankManager;
    }
}