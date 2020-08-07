package com.takeoff.models.account;

import com.takeoff.utils.Constants;
import com.takeoff.utils.FileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountsStorage {

    private final Map<Long, AccountModel> accountsMap = new HashMap<>();

    public AccountsStorage() {
        FileReader fileReader = new FileReader();
        List<AccountModel> accountsList = fileReader.getAccountsFromCSVFile(Constants.PATH_TO_FILE);
        fillAccountsMap(accountsList);
    }

    public AccountsStorage(String path) {
        FileReader fileReader = new FileReader();
        List<AccountModel> accountsList = fileReader.getAccountsFromCSVFile(path);
        fillAccountsMap(accountsList);
    }

    public void fillAccountsMap(List<AccountModel> accountModelList) {
        accountModelList.forEach(it -> accountsMap.put(it.getId(), it));
    }

    public boolean isAccountExists(long id) {
        return accountsMap.get(id) != null;
    }

    public AccountModel getAccount(long id) {
        return accountsMap.get(id);
    }
}
