package com.takeoff.services;

import com.takeoff.models.account.AccountsStorage;
import com.takeoff.models.atm.Machine;
import com.takeoff.utils.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OperationsLogicTest {

    private static OperationsLogicService operationsLogicService;
    private static final long validUserId = 3216549871L;
    private static final String validUserPin = "1320";
    private static final long invalidUserId = 3333333333L;
    private static final String invalidUserPin = "4567";
    private static final long overdraftUserId = 1231231231L;
    private static final String overdraftUserPin = "9988";

    @Before
    public void setUp() {
        operationsLogicService = new OperationsLogicService();
        AccountsService.accountsStorage = new AccountsStorage("test_data.csv");
    }

    @Test
    public void authorizationSuccessTest() {
        String commandResult = operationsLogicService.authorization(validUserId, validUserPin);
        Assert.assertEquals(Constants.AUTHORIZE_SUCCESSFUL, commandResult);
    }

    @Test
    public void authorizationFailedInvalidPinTest() {
        String commandResult = operationsLogicService.authorization(validUserId, invalidUserPin);
        Assert.assertEquals(Constants.AUTHORIZE_ERR, commandResult);
    }

    @Test
    public void authorizationFailedInvalidIdTest() {
        String commandResult = operationsLogicService.authorization(invalidUserId, validUserPin);
        Assert.assertEquals(Constants.AUTHORIZE_ERR, commandResult);
    }

    @Test
    public void getBalanceTest() {
        operationsLogicService.authorization(validUserId, validUserPin);
        String commandResult = operationsLogicService.balance();
        Assert.assertEquals(String.format(Constants.CURRENT_BALANCE, 1250d), commandResult);
    }

    @Test
    public void withdrawalTest() {
        String amount = "1000";
        operationsLogicService.authorization(validUserId, validUserPin);
        String commandResult = operationsLogicService.withdrawLogicFlow(amount);
        String expectedResult = String.format(Constants.AMOUNT_DISPENSED, Double.valueOf(amount))
                + String.format(Constants.CURRENT_BALANCE, 250d);
        Assert.assertEquals(expectedResult, commandResult);
    }

    @Test
    public void withdrawalWithFeeTest() {
        String amount = "1300";
        operationsLogicService.authorization(validUserId, validUserPin);
        String commandResult = operationsLogicService.withdrawLogicFlow(amount);
        String expectedResult = String.format(Constants.AMOUNT_DISPENSED, Double.valueOf(amount))
                + String.format(Constants.OVERDRAFT_BALANCE, -55d);
        Assert.assertEquals(expectedResult, commandResult);
    }

    @Test
    public void withdrawalOverdraftAccountTest() {
        String amount = "1000";
        operationsLogicService.authorization(overdraftUserId, overdraftUserPin);
        String commandResult = operationsLogicService.withdrawLogicFlow(amount);
        Assert.assertEquals(Constants.OVERDRAW_ACCOUNT_ERR, commandResult);
    }

    @Test
    public void withdrawalEmptyMachineTest() {
        String amount = "1000";
        Machine machine = Machine.getInstance();
        machine.setBalance(0d);
        operationsLogicService.authorization(validUserId, validUserPin);
        String commandResult = operationsLogicService.withdrawLogicFlow(amount);
        Assert.assertEquals(Constants.ATM_EMPTY_ERR, commandResult);
    }

    @Test
    public void withdrawalAdjustedAmountTest() {
        String amount = "10100";
        operationsLogicService.authorization(validUserId, validUserPin);
        String commandResult = operationsLogicService.withdrawLogicFlow(amount);
        Assert.assertEquals(String.format(Constants.NOT_FULL_AMOUNT_ERR, Constants.INITIAL_ATM_BALANCE), commandResult);
    }

    @Test
    public void withdrawalInvalidAmountTest() {
        String amount = "-250";
        operationsLogicService.authorization(validUserId, validUserPin);
        String commandResult = operationsLogicService.withdrawLogicFlow(amount);
        Assert.assertEquals(Constants.INVALID_AMOUNT_ERR, commandResult);
    }

    @Test
    public void depositTest() {
        String amount = "1000";
        operationsLogicService.authorization(validUserId, validUserPin);
        String commandResult = operationsLogicService.deposit(amount);
        Assert.assertEquals(String.format(Constants.CURRENT_BALANCE, 2250d), commandResult);
    }

    @Test
    public void depositInvalidAmountTest() {
        String amount = "-250";
        operationsLogicService.authorization(validUserId, validUserPin);
        String commandResult = operationsLogicService.deposit(amount);
        Assert.assertEquals(Constants.INVALID_AMOUNT_ERR, commandResult);
    }

    @Test
    public void emptyHistoryTest() {
        operationsLogicService.authorization(validUserId, validUserPin);
        String commandResult = operationsLogicService.showHistory();
        Assert.assertEquals(Constants.NO_HISTORY, commandResult);
    }

    @Test
    public void logoutTest() {
        operationsLogicService.authorization(validUserId, validUserPin);
        String commandResult = operationsLogicService.logout();
        Assert.assertEquals(String.format(Constants.LOG_OUT_SUCCESS, validUserId), commandResult);
    }

    @Test
    public void logoutInvalidTest() {
        AccountsService.activeAccount = null;
        String commandResult = operationsLogicService.logout();
        Assert.assertEquals(Constants.NO_AUTHORIZED_ACC, commandResult);
    }
}
