package com.abc.account.impl;

import com.abc.account.Account;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by Vlad Kostiuchenko on 7/24/16.
 * E-mail: sadkou@gmail.com
 * cell: 6467063785
 */

public class Checking extends Account{
    private static final double RATE_PER_MONTH = 0.001;
    public static final String NAME = "Checking";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public double getInterestEarned() {

        List<Object[]> lst = getTransactions();
        double percentPerDay = RATE_PER_MONTH / DAY_PER_MONTH;

        LocalDate previousDate = null;
        double totalInterestEarned = 0;
        double currentAmount = 0;
        double previousAmount = 0;
        long currentDays = 0;


        for (int i=0; i<lst.size(); i++) {
            LocalDate currentDate = (LocalDate) lst.get(i)[0];
            currentAmount += (Double) lst.get(i)[1];

            if (previousDate != null) {
                currentDays = ChronoUnit.DAYS.between(previousDate, currentDate);
                totalInterestEarned += percentPerDay * currentDays * previousAmount;
            }

            previousAmount = currentAmount;
            previousDate = currentDate;
        }

        if (previousDate != null) {
            currentDays = ChronoUnit.DAYS.between(previousDate, LocalDate.now());
            totalInterestEarned += percentPerDay * currentDays * previousAmount;
        }

        return totalInterestEarned;
    }
}
