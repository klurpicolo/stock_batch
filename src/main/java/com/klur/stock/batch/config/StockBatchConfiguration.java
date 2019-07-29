package com.klur.stock.batch.config;

import com.klur.stock.JobCompletionNotificationListener;
import com.klur.stock.backend.entity.StockPriceDTO;
import com.klur.stock.batch.processor.StockItemProcessor;
import com.klur.stock.batch.reader.RestStockItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class StockBatchConfiguration {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<StockPriceDTO> StockReader() throws IOException {
        return new RestStockItemReader();
    }

    @Bean
    public StockItemProcessor StockProcessor() {
        return new StockItemProcessor();
    }

    @Bean
    public JpaItemWriter<StockPriceDTO> StockJpaWriter(){
        JpaItemWriter<StockPriceDTO> stockPriceJpaItemWriter = new JpaItemWriter<StockPriceDTO>();
        stockPriceJpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return stockPriceJpaItemWriter;
    }

    @Bean
    public Job importStockJob(JobCompletionNotificationListener listener, Step step1){
        return jobBuilderFactory.get("importStockJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step stockStep1 (JpaItemWriter<StockPriceDTO> writer) throws IOException {
        return stepBuilderFactory.get("stockStep1")
                .<StockPriceDTO, StockPriceDTO> chunk(1)
                .reader(StockReader())
                .processor(StockProcessor())
                .writer(writer)
                .build();
    }

}
