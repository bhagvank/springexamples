package org.springexamples.batch;

import org.springexamples.batch.model.BatchTransaction;
import org.springexamples.batch.service.CustomItemProcessor;
import org.springexamples.batch.service.CustomSkipPolicy;
import org.springexamples.batch.service.MissingUsernameException;
import org.springexamples.batch.service.NegativeAmountException;
import org.springexamples.batch.service.RecordFieldSetMapper;
import org.springexamples.batch.service.SkippingItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.text.ParseException;

public class SpringBatchConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("input/record.csv")
    private Resource inputCsv;

    @Value("input/recordWithInvalidData.csv")
    private Resource invalidInputCsv;

    @Value("file:xml/output.xml")
    private Resource outputXml;

    public ItemReader<BatchTransaction> itemReader(Resource inputData) throws UnexpectedInputException, ParseException {
        FlatFileItemReader<BatchTransaction> reader = new FlatFileItemReader<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"username", "userid", "transactiondate", "amount"};
        tokenizer.setNames(tokens);
        reader.setResource(inputData);
        DefaultLineMapper<BatchTransaction> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public ItemProcessor<BatchTransaction, BatchTransaction> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemProcessor<BatchTransaction, BatchTransaction> skippingItemProcessor() {
        return new SkippingItemProcessor();
    }

    @Bean
    public ItemWriter<BatchTransaction> itemWriter(Marshaller marshaller) {
        StaxEventItemWriter<BatchTransaction> itemWriter = new StaxEventItemWriter<>();
        itemWriter.setMarshaller(marshaller);
        itemWriter.setRootTagName("transactionRecord");
        itemWriter.setResource(outputXml);
        return itemWriter;
    }

    @Bean
    public Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(BatchTransaction.class);
        return marshaller;
    }

    @Bean
    protected Step step1(@Qualifier("itemProcessor") ItemProcessor<BatchTransaction, BatchTransaction> processor, ItemWriter<BatchTransaction> writer) throws ParseException {
        return stepBuilderFactory
                .get("step1")
                .<BatchTransaction, BatchTransaction> chunk(10)
                .reader(itemReader(inputCsv))
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobBuilderFactory.get("firstBatchJob").start(step1).build();
    }

    @Bean
    public Step skippingStep(@Qualifier("skippingItemProcessor") ItemProcessor<BatchTransaction, BatchTransaction> processor,
                             ItemWriter<BatchTransaction> writer) throws ParseException {
        return stepBuilderFactory
                .get("skippingStep")
                .<BatchTransaction, BatchTransaction>chunk(10)
                .reader(itemReader(invalidInputCsv))
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipLimit(2)
                .skip(MissingUsernameException.class)
                .skip(NegativeAmountException.class)
                .build();
    }

    @Bean(name = "skippingBatchJob")
    public Job skippingJob(@Qualifier("skippingStep") Step skippingStep) {
        return jobBuilderFactory
                .get("skippingBatchJob")
                .start(skippingStep)
                .build();
    }

    @Bean
    public Step skipPolicyStep(@Qualifier("skippingItemProcessor") ItemProcessor<BatchTransaction, BatchTransaction> processor,
                               ItemWriter<BatchTransaction> writer) throws ParseException {
        return stepBuilderFactory
                .get("skipPolicyStep")
                .<BatchTransaction, BatchTransaction>chunk(10)
                .reader(itemReader(invalidInputCsv))
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(new CustomSkipPolicy())
                .build();
    }

    @Bean(name = "skipPolicyBatchJob")
    public Job skipPolicyBatchJob(@Qualifier("skipPolicyStep") Step skipPolicyStep) {
        return jobBuilderFactory
                .get("skipPolicyBatchJob")
                .start(skipPolicyStep)
                .build();
    }

}
