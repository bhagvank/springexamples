package org.springexamples.batch.service;

import org.springexamples.batch.model.BatchTransaction;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecordFieldSetMapper implements FieldSetMapper<BatchTransaction> {

    public BatchTransaction mapFieldSet(FieldSet fieldSet) throws BindException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyy");

        BatchTransaction transaction = new BatchTransaction();

        transaction.setUsername(fieldSet.readString("username"));
        transaction.setUserId(fieldSet.readInt("userid"));
        transaction.setAmount(fieldSet.readDouble(3));

        String dateString = fieldSet.readString(2);
        transaction.setTransactionDate(LocalDate.parse(dateString, formatter).atStartOfDay());

        return transaction;

    }

}
