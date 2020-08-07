package com.takeoff.models.account;

public class HistoricalOperation implements Comparable<HistoricalOperation> {

    private final long time;
    private final double amount;
    private final double balance;
    private final OperationType operationType;

    public HistoricalOperation(double amount, OperationType operationType, double balance) {
        time = System.currentTimeMillis();
        this.amount = amount;
        this.operationType = operationType;
        this.balance = balance;
    }

    public long getTime() {
        return time;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalance() {
        return balance;
    }

    public OperationType getType() {
        return operationType;
    }

    @Override
    public int compareTo(HistoricalOperation historicalOperation) {
        if (this.getTime() > historicalOperation.getTime()) {
            return 1;
        } else {
            return -1;
        }
    }
}
