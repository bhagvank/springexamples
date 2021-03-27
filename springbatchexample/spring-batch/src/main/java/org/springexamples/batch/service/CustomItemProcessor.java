package org.springexamples.batch.service;

import org.springexamples.batch.model.BatchTransaction;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<BatchTransaction, BatchTransaction> {

    public BatchTransaction process(BatchTransaction item) {
        System.out.println("Processing..." + item);
        return item;
    }
}