package com.takeoff.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Constants {

    // ATM properties
    public static final double INITIAL_ATM_BALANCE = 10000.00;
    public static final double ATM_MIN_DENOMINATION = 20;

    // Account properties
    public static final double OVERDRAFT_FEE = 5;
    public static final int SESSION_TIME_SECONDS = 120;
    public static final String HISTORY_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String WITHDRAW_SIGN = "-";

    // Data file
    public static final String PATH_TO_FILE = "accounts.csv";

    // ATM commands list
    public static final String AUTHORIZE_CMD = "authorize";
    public static final String WITHDRAW_CMD = "withdraw";
    public static final String DEPOSIT_CMD = "deposit";
    public static final String BALANCE_CMD = "balance";
    public static final String HISTORY_CMD = "history";
    public static final String LOGOUT_CMD = "logout";
    public static final String END_CMD = "end";
    public static final List<String> ALL_COMMANDS = new ArrayList<>(Arrays.asList(AUTHORIZE_CMD, WITHDRAW_CMD,
            DEPOSIT_CMD, BALANCE_CMD, HISTORY_CMD, LOGOUT_CMD, END_CMD));

    // ATM messages
    public static final String AUTHORIZE_ERR = "Authorization failed.";
    public static final String AUTHORIZE_SUCCESSFUL = "Authorization passed.";
    public static final String AUTHORIZE_REQUEST = "Authorization failed.";
    public static final String INVALID_INPUT_ERR = "Invalid input.";
    public static final String OVERDRAW_ACCOUNT_ERR = "Your account is overdrawn! You may not make withdrawals at this time.";
    public static final String ATM_EMPTY_ERR = "Unable to process your withdrawal at this time.";
    public static final String INVALID_AMOUNT_ERR = "Invalid amount.";
    public static final String NOT_FULL_AMOUNT_ERR = "Unable to dispense full amount requested at this time. You can try withdraw %.2f";
    public static final String AMOUNT_DISPENSED = "Amount dispensed: %.2f\n";
    public static final String CURRENT_BALANCE = "Current balance: %.2f";
    public static final String OVERDRAFT_BALANCE = "You have been charged an overdraft fee of $5. Current balance: %.2f";
    public static final String NO_HISTORY = "No history found.";
    public static final String LOG_OUT_SUCCESS = "Account %d logged out";
    public static final String NO_AUTHORIZED_ACC = "No account is currently authorized.";

    // File upload messages
    public static final String UPLOAD_FILE_ERR = "Error occurred during CSV file upload!\nPlease double-check file content before upload!";
}
