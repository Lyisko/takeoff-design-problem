package com.takeoff.models.atm;

import com.takeoff.utils.Constants;

public class Machine {

    private static Machine instance;
    private static double balance;

    private Machine() {
        balance = Constants.INITIAL_ATM_BALANCE;
    }

    public static Machine getInstance() {
        if (instance == null) {
            instance = new Machine();
        }
        return instance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        Machine.balance = balance;
    }

}
