package org.springexamples.batch.service;

import org.springexamples.batch.model.BatchTransaction;
import org.springframework.batch.item.ItemProcessor;

public class SkippingItemProcessor implements ItemProcessor<BatchTransaction, BatchTransaction> {

    @Override
    public BatchTransaction process(BatchTransaction transaction) {

        System.out.println("SkippingItemProcessor: " + transaction);

        if (transaction.getUsername() == null || transaction.getUsername().isEmpty()) {
            throw new MissingUsernameException();
        }

        double txAmount = transaction.getAmount();
        if (txAmount < 0) {
            throw new NegativeAmountException(txAmount);
        }

        return transaction;
    }
}
