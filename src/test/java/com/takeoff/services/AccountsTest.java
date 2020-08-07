package com.takeoff.services;

import com.takeoff.models.account.AccountModel;
import com.takeoff.models.account.AccountsStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountsTest {

    private static AccountsService accountsService;
    private static AccountModel accountModel;
    private static final long accountId = 1234567890L;

    @BeforeClass
    public static void classSetUp() {
        accountsService = new AccountsService();
    }

    @Before
    public void setUp() {
        AccountsService.accountsStorage = new AccountsStorage("test_data.csv");
        accountModel = AccountsService.accountsStorage.getAccount(accountId);
        AccountsService.activeAccount = accountModel;
    }

    @Test
    public void checkBalanceTest() {
        Assert.assertEquals(10000, accountModel.getBalance(), 0);
    }

    @Test
    public void checkWithdrawalTest() {
        accountsService.withdraw(780);
        Assert.assertEquals(9220, accountModel.getBalance(), 0);
    }

    @Test
    public void depositTest() {
        accountsService.deposit(500);
        Assert.assertEquals(10500, accountModel.getBalance(), 0);
    }

    @Test
    public void overdraftFeeTest() {
        accountsService.debitOverdraftFee();
        Assert.assertEquals(9995, accountModel.getBalance(), 0);
    }
}
