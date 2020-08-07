package com.takeoff.services;

import com.takeoff.utils.Constants;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class InputValidationTest {

    private static ConsoleService consoleService;
    private static final String[] validCommand = new String[]{"authorize", "1111111", "2222"};
    private static final String[] validCommand1 = new String[]{"withdraw", "3333333"};
    private static final String[] validCommand2 = new String[]{"deposit", "3333333"};
    private static final String[] invalidCommand = new String[]{"deposit", "4444444", "55555", "6666"};
    private static final String[] invalidCommand1 = new String[]{};
    private static final String[] invalidCommand2 = new String[]{"deposit", "test"};

    @BeforeClass
    public static void classSetUp() {
        consoleService = new ConsoleService();
    }

    @Test
    public void validCommandsTest() {
        for (String input : Constants.ALL_COMMANDS) {
            consoleService.userInput = new String[]{input};
            Assert.assertTrue(consoleService.isInputValid());
        }
    }

    @Test
    public void validInputsTest() {
        consoleService.userInput = validCommand;
        Assert.assertTrue(consoleService.isInputValid());
        consoleService.userInput = validCommand1;
        Assert.assertTrue(consoleService.isInputValid());
        consoleService.userInput = validCommand2;
        Assert.assertTrue(consoleService.isInputValid());
    }

    @Test
    public void invalidInputsTest() {
        consoleService.userInput = invalidCommand;
        Assert.assertFalse(consoleService.isInputValid());
        consoleService.userInput = invalidCommand1;
        Assert.assertFalse(consoleService.isInputValid());
        consoleService.userInput = invalidCommand2;
        Assert.assertFalse(consoleService.isInputValid());
    }
}
