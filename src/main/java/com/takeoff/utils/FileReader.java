package com.takeoff.utils;

import com.takeoff.models.account.AccountModel;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FileReader {

    public List<AccountModel> getAccountsFromCSVFile(String fileName) {
        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(fileName)).getFile());
        List<AccountModel> accountModelList = null;
        MappingIterator<AccountModel> accountIterator;
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper
                .typedSchemaFor(AccountModel.class)
                .withHeader()
                .withColumnSeparator(',');
        try {
            accountIterator = csvMapper.readerWithTypedSchemaFor(AccountModel.class).with(csvSchema).readValues(file);
            accountModelList = accountIterator.readAll();
        } catch (IOException e) {
            System.out.println(Constants.UPLOAD_FILE_ERR);
            System.out.println(e.getMessage());
        }
        return accountModelList;
    }
}
