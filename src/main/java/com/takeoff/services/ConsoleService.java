package com.takeoff.services;

import com.takeoff.utils.Constants;

import java.util.Scanner;

public class ConsoleService {

    public String[] userInput;

    public void getUserInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        userInput = input.split(" ");
    }

    public boolean isInputValid() {
        if (userInput.length > 0 && userInput.length < 4 && Constants.ALL_COMMANDS.contains(userInput[0])) {
            return areInputValuesCorrect();
        } else {
            return false;
        }
    }

    private boolean areInputValuesCorrect() {
        if (userInput.length > 1) {
            for (int i = 1; i < userInput.length; i++) {
                try {
                    Double.parseDouble(userInput[i]);
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return true;
    }
}
