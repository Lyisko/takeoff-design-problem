package com.takeoff.services;

import com.takeoff.models.account.AccountModel;
import com.takeoff.models.account.AccountsStorage;
import com.takeoff.models.account.HistoricalOperation;
import com.takeoff.models.account.OperationType;
import com.takeoff.utils.Constants;

public class AccountsService {

    public static AccountModel activeAccount;
    public static AccountsStorage accountsStorage = new AccountsStorage();

    public void authorize(long id) {
        activeAccount = accountsStorage.getAccount(id);
        activeAccount.setAuthorized(true);
        activeAccount.setAuthorizationTime(System.currentTimeMillis());
    }

    public void withdraw(double amount) {
        activeAccount.setBalance(activeAccount.getBalance() - amount);
        activeAccount.addOperation(new HistoricalOperation(amount, OperationType.WITHDRAW, activeAccount.getBalance()));
    }

    public void debitOverdraftFee() {
        activeAccount.setBalance(activeAccount.getBalance() - Constants.OVERDRAFT_FEE);
        activeAccount.addOperation(new HistoricalOperation(Constants.OVERDRAFT_FEE, OperationType.WITHDRAW, activeAccount.getBalance()));
    }

    public void deposit(double amount) {
        activeAccount.setBalance(activeAccount.getBalance() + amount);
        activeAccount.addOperation(new HistoricalOperation(amount, OperationType.DEPOSIT, activeAccount.getBalance()));
    }

    public long logout() {
        long logoutId = activeAccount.getId();
        activeAccount = null;
        return logoutId;
    }

    public void updateTime() {
        activeAccount.setAuthorizationTime(System.currentTimeMillis());
    }
}
