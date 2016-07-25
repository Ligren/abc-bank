package com.abc;

import java.time.LocalDate;

/**
 * Created by Vlad Kostiuchenko on 7/24/16.
 * E-mail: sadkou@gmail.com
 * cell: 6467063785
 */

public class Transaction {
    private final double amount;
    private final LocalDate transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
    }

    //For testing
    public Transaction(double amount, LocalDate transactionDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return transactionDate;
    }

}
