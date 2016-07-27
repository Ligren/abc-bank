package com.abc;

import com.abc.account.Account;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Vlad Kostiuchenko on 7/24/16.
 * E-mail: sadkou@gmail.com
 * cell: 6467063785
 */


public class Customer {
    private String name;
    private final List<Account> accounts = new ArrayList<>();
    public static NumberFormat NF = NumberFormat.getCurrencyInstance(Locale.US);
    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        return accounts.stream().mapToDouble(a -> a.getInterestEarned()).sum();
    }

    public String getStatement() {


        final String[] statement = {"Statement for " + name + "\n"};
        double sum = accounts.stream().peek(a -> statement[0] += "\n" + statementForAccount(a) + "\n").mapToDouble(a -> a.getTotal()).sum();
        statement[0] += "\nTotal In All Accounts " + NF.format(sum);
        return statement[0];
    }

    private String statementForAccount(Account a) {
        Function<Object[], String> func = t -> {
            return (((double) t[1] > 0) ? "deposit " : "withdrawal ") + "Date: " + ((LocalDate) t[0]).format(DATE_FORMATTER) + ", Amount: " + NF.format((double) t[1]) + "\n";
        };

        return a.getName() + "\n" +
                a.getTransactions().stream().map(func).collect(Collectors.joining()) + "Total: " + NF.format(a.getTotal());
    }
}
