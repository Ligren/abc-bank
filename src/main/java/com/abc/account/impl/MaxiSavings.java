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

public class MaxiSavings extends Account {

    private static final double RATE_PER_MONTH_WITHDRAWALS = 0.001;
    private static final double RATE_PER_MONTH_NO_WITHDRAWALS = 0.05;
    public static final String NAME = "Maxi-Savings";

    @Override
    public String getName() {
        return NAME;
    }

// Task: Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%

    // I did next logic:
// - if withdrawals was in past 10 days after deposit, so rate will be 0.1% from deposit date to next withdrawals date,
// - if withdrawals was more then after 10 days after deposit so rate will be 5% from deposit date to next withdrawals date.
    @Override
    public synchronized double getInterestEarned() {

        List<Object[]> lst = getTransactions();
        double percentPerDayWithdrawals = RATE_PER_MONTH_WITHDRAWALS / DAY_PER_MONTH;
        double percentPerDayNoWithdrawals = RATE_PER_MONTH_NO_WITHDRAWALS / DAY_PER_MONTH;

        LocalDate previousDate = null;
        LocalDate lastWithdrawalsDate = null;
        long daysBetweenDepositAndWithdrawals;
        double totalInterestEarned = 0;
        double currentAmount = 0;
        double previousAmount = 0;
        long currentDays = 0;


        for (int i=0; i<lst.size(); i++) {
            LocalDate currentDate = (LocalDate) lst.get(i)[0];
            currentAmount += (Double) lst.get(i)[1];


            if ((Double) lst.get(i)[1] < 0) lastWithdrawalsDate = currentDate;
            if (lastWithdrawalsDate == null)
                daysBetweenDepositAndWithdrawals = 11; //more than 10
            else
                if (lastWithdrawalsDate.equals(currentDate))
                    daysBetweenDepositAndWithdrawals = ChronoUnit.DAYS.between(previousDate, lastWithdrawalsDate);
                else
                    daysBetweenDepositAndWithdrawals = ChronoUnit.DAYS.between(lastWithdrawalsDate, currentDate);

            if (previousDate != null) {
                currentDays = ChronoUnit.DAYS.between(previousDate, currentDate);

                if (daysBetweenDepositAndWithdrawals > 10) {
                    totalInterestEarned += percentPerDayNoWithdrawals * currentDays * previousAmount;
                } else {
                    totalInterestEarned += percentPerDayWithdrawals * currentDays * previousAmount;
                }
            }

            previousDate = currentDate;
            previousAmount = currentAmount;
        }

        LocalDate currentDate = LocalDate.now();

        if (lastWithdrawalsDate == null)
            daysBetweenDepositAndWithdrawals = 11; //more than 10
        else
            if (lastWithdrawalsDate.equals(currentDate))
                daysBetweenDepositAndWithdrawals = ChronoUnit.DAYS.between(previousDate, lastWithdrawalsDate);
            else
                daysBetweenDepositAndWithdrawals = ChronoUnit.DAYS.between(lastWithdrawalsDate, currentDate);

        if (previousDate != null) {
            currentDays = ChronoUnit.DAYS.between(previousDate, currentDate);

            if (daysBetweenDepositAndWithdrawals > 10) {
                totalInterestEarned += percentPerDayNoWithdrawals * currentDays * previousAmount;
            } else {
                totalInterestEarned += percentPerDayWithdrawals * currentDays * previousAmount;
            }
        }

        return totalInterestEarned;
    }
}
