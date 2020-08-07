package com.takeoff.services;

import com.takeoff.models.atm.Machine;

public class ATMService {

    public Machine machine = Machine.getInstance();

    public boolean isEnoughMoney(double amount) {
        return (machine.getBalance() - amount) > 0;
    }

    public void withdrawMoney(double amount) {
        machine.setBalance(machine.getBalance() - amount);
    }
}
