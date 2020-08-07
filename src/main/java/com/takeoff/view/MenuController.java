package com.takeoff.view;

import com.takeoff.services.AccountsService;
import com.takeoff.services.ConsoleService;
import com.takeoff.services.OperationsLogicService;
import com.takeoff.utils.Constants;

public class MenuController {

    private OperationsLogicService operationsLogicService = new OperationsLogicService();
    private ConsoleService consoleService = new ConsoleService();
    private String commandResult;

    public void runATM() {
        boolean isProgramRunning = true;
        while (isProgramRunning) {
            consoleService.getUserInput();
            commandResult = Constants.INVALID_INPUT_ERR;
            if (consoleService.isInputValid()) {
                switch (consoleService.userInput[0]) {
                    case Constants.END_CMD:
                        isProgramRunning = false;
                        break;
                    case Constants.AUTHORIZE_CMD:
                        if (consoleService.userInput.length == 3) {
                            commandResult = operationsLogicService.authorization(
                                    Long.parseLong(consoleService.userInput[1]), consoleService.userInput[2]);
                        }
                        break;
                    case Constants.WITHDRAW_CMD:
                        if (isNotAuthorized()) break;
                        if (consoleService.userInput.length == 2) {
                            commandResult = operationsLogicService.withdrawLogicFlow(consoleService.userInput[1]);
                        }
                        break;
                    case Constants.DEPOSIT_CMD:
                        if (isNotAuthorized()) break;
                        if (consoleService.userInput.length == 2) {
                            commandResult = operationsLogicService.deposit(consoleService.userInput[1]);
                        }
                        break;
                    case Constants.BALANCE_CMD:
                        if (isNotAuthorized()) break;
                        commandResult = operationsLogicService.balance();
                        break;
                    case Constants.HISTORY_CMD:
                        if (isNotAuthorized()) break;
                        commandResult = operationsLogicService.showHistory();
                        break;
                    case Constants.LOGOUT_CMD:
                        commandResult = operationsLogicService.logout();
                        break;
                    default:
                        commandResult = Constants.INVALID_INPUT_ERR;
                        break;
                }
            }
            System.out.println(commandResult);
        }
    }

    private boolean isNotAuthorized() {
        if (AccountsService.activeAccount == null || !AccountsService.activeAccount.isAuthorized()) {
            commandResult = Constants.AUTHORIZE_REQUEST;
            return true;
        } else {
            return false;
        }
    }
}

