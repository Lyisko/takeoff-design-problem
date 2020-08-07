package com.takeoff.services;

import com.takeoff.models.account.AccountModel;
import com.takeoff.models.account.HistoricalOperation;
import com.takeoff.models.account.OperationType;
import com.takeoff.utils.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class OperationsLogicService {

    private final AccountsService accountsService = new AccountsService();
    private final ATMService atmService = new ATMService();

    public String authorization(long id, String pin) {
        if (AccountsService.accountsStorage.isAccountExists(id)) {
            AccountModel account = AccountsService.accountsStorage.getAccount(id);
            if (account.getPin().equals(pin)) {
                accountsService.authorize(id);
                return Constants.AUTHORIZE_SUCCESSFUL;
            }
        }
        return Constants.AUTHORIZE_ERR;
    }

    public String withdrawLogicFlow(String input) {
        accountsService.updateTime();
        double amount = Double.parseDouble(input);
        if (AccountsService.activeAccount.getBalance() < 0) {
            return Constants.OVERDRAW_ACCOUNT_ERR;
        }
        if (atmService.machine.getBalance() < Constants.ATM_MIN_DENOMINATION) {
            return Constants.ATM_EMPTY_ERR;
        }
        if (amount > 0 && (amount % Constants.ATM_MIN_DENOMINATION == 0)) {
            if (atmService.isEnoughMoney(amount)) {
                return withdrawAmount(amount);
            } else {
                double adjustedAmount = Constants.ATM_MIN_DENOMINATION *
                        (Math.floor(Math.abs((atmService.machine.getBalance() / Constants.ATM_MIN_DENOMINATION))));
                return String.format(Constants.NOT_FULL_AMOUNT_ERR, adjustedAmount);
            }
        }
        return Constants.INVALID_AMOUNT_ERR;
    }

    private String withdrawAmount(double amount) {
        atmService.withdrawMoney(amount);
        accountsService.withdraw(amount);
        String accountBalance = String.format(Constants.AMOUNT_DISPENSED, amount);
        if (AccountsService.activeAccount.getBalance() > 0) {
            return accountBalance + String.format(Constants.CURRENT_BALANCE, AccountsService.activeAccount.getBalance());
        } else {
            accountsService.debitOverdraftFee();
            return accountBalance + String.format(Constants.OVERDRAFT_BALANCE, AccountsService.activeAccount.getBalance());
        }
    }

    public String deposit(String input) {
        accountsService.updateTime();
        double amount = Double.parseDouble(input);
        if (amount > 0) {
            accountsService.deposit(amount);
            return String.format(Constants.CURRENT_BALANCE, AccountsService.activeAccount.getBalance());
        } else {
            return Constants.INVALID_AMOUNT_ERR;
        }
    }

    public String balance() {
        accountsService.updateTime();
        return String.format(Constants.CURRENT_BALANCE, AccountsService.activeAccount.getBalance());
    }

    public String showHistory() {
        accountsService.updateTime();
        if (AccountsService.activeAccount.getOperationsHistory().size() == 0) {
            return Constants.NO_HISTORY;
        } else {
            AccountsService.activeAccount.getOperationsHistory().sort(Collections.reverseOrder());
            DateFormat dateFormat = new SimpleDateFormat(Constants.HISTORY_DATE_TIME_FORMAT);
            StringBuilder operationsHistory = new StringBuilder();
            for (HistoricalOperation operation : AccountsService.activeAccount.getOperationsHistory()) {
                String dateTime = dateFormat.format(new Date(operation.getTime()));
                String type = operation.getType().equals(OperationType.WITHDRAW) ? Constants.WITHDRAW_SIGN : "";
                operationsHistory
                        .append(dateTime)
                        .append(" ")
                        .append(type)
                        .append(String.format("%.2f", Math.round(operation.getAmount() * 100.0) / 100.0))
                        .append(" ")
                        .append(String.format("%.2f", Math.round(operation.getBalance() * 100.0) / 100.0))
                        .append("\n");
            }
            return operationsHistory.toString();
        }
    }

    public String logout() {
        if (AccountsService.activeAccount != null && AccountsService.activeAccount.isAuthorized()) {
            long accountId = accountsService.logout();
            return String.format(Constants.LOG_OUT_SUCCESS, accountId);
        } else {
            return Constants.NO_AUTHORIZED_ACC;
        }
    }
}
