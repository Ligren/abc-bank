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

public class Savings extends Account {

    private static final double RATE_PER_MONTH_FOR_THE_FIRST_K = 0.001;
    private static final double RATE_PER_MONTH_THEN = 0.002;
    public static final String NAME = "Savings";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public synchronized double getInterestEarned() {

        List<Object[]> lst = getTransactions();
        double percentPerDayForFirstK = RATE_PER_MONTH_FOR_THE_FIRST_K / DAY_PER_MONTH;
        double percentPerDayThen = RATE_PER_MONTH_THEN / DAY_PER_MONTH;

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
                if (previousAmount > 1000) {
                    totalInterestEarned += percentPerDayForFirstK * currentDays * 1000;
                    totalInterestEarned += percentPerDayThen * currentDays * (previousAmount - 1000);
                } else totalInterestEarned += percentPerDayForFirstK * currentDays * previousAmount;
            }

            previousDate = currentDate;
            previousAmount = currentAmount;
        }

        currentDays = ChronoUnit.DAYS.between(previousDate, LocalDate.now());
        if (previousAmount > 1000) {
            totalInterestEarned += percentPerDayForFirstK * currentDays * 1000;
            totalInterestEarned += percentPerDayThen * currentDays * (previousAmount - 1000);
        } else totalInterestEarned += percentPerDayForFirstK * currentDays * previousAmount;

        return totalInterestEarned;
    }
}
