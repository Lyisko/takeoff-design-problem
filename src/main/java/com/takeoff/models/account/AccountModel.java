package com.takeoff.models.account;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.takeoff.utils.Constants;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"id", "pin", "balance"})
public class AccountModel {

    private long id;
    private String pin;
    private double balance;
    private List<HistoricalOperation> operationsHistory;
    private boolean isAuthorized;
    private long authorizationTime;

    public AccountModel() {
        operationsHistory = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<HistoricalOperation> getOperationsHistory() {
        return operationsHistory;
    }

    public void addOperation(HistoricalOperation historicalOperations) {
        operationsHistory.add(historicalOperations);
    }

    public boolean isAuthorized() {
        long diff = System.currentTimeMillis() - authorizationTime;
        if (diff > (Constants.SESSION_TIME_SECONDS * 1000)) {
            isAuthorized = false;
        }
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public void setAuthorizationTime(long authorizationTime) {
        this.authorizationTime = authorizationTime;
    }
}
