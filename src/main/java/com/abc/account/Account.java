package com.abc.account;

import com.abc.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Account {
    private double total;
    private final List<Transaction> transactions = new ArrayList<>();
    public static final int DAY_PER_MONTH = 30;

    abstract public String getName();

    public double getTotal() {
        return total;
    }

    //I don`t want to give access directly to transaction
    public List<Object[]> getTransactions() {
        Function<Transaction, Object[]> func = t -> {
            Object[] arr = new Object[2];
            arr[0] = t.getDate();
            arr[1] = t.getAmount();
            return arr;
        };

        return transactions.stream().map(func).collect(Collectors.toList());
    }

    public abstract double getInterestEarned();

    public void deposit(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Wrong amount for deposit!");

        total += amount;
        transactions.add(new Transaction(amount));
    }

    public void withdraw(double amount) {
        if (amount > total) throw new IllegalArgumentException("Wrong amount for withdraw! Sum on your account is " + total);

        total -= amount;
        transactions.add(new Transaction(-amount));
    }

    //For testing
    public void addTransaction(Transaction tr) {
        transactions.add(tr);
    }


}
