package com.takeoff.services;

import com.takeoff.models.atm.Machine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ATMTest {

    private static ATMService atmService;
    private static Machine machine;

    @BeforeClass
    public static void classSetUp() {
        atmService = new ATMService();
        machine = Machine.getInstance();
    }

    @Before
    public void setUp() {
        machine.setBalance(10000);
    }

    @Test
    public void checkWithdrawalTest() {
        atmService.withdrawMoney(780);
        Assert.assertEquals(9220, machine.getBalance(), 0);
    }

    @Test
    public void checkEnoughMoney() {
        Assert.assertTrue(atmService.isEnoughMoney(780));
    }

    @Test
    public void checkNotEnoughMoney() {
        Assert.assertFalse(atmService.isEnoughMoney(50000));
    }
}
