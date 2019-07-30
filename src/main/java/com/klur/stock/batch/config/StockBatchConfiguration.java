package com.klur.stock.batch.config;

import com.klur.stock.JobCompletionNotificationListener;
import com.klur.stock.backend.entity.StockPriceDTO;
import com.klur.stock.backend.service.StockAssetService;
import com.klur.stock.batch.processor.StockItemProcessor;
import com.klur.stock.batch.reader.RestStockItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class StockBatchConfiguration {

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public StockBatchConfiguration(EntityManagerFactory entityManagerFactory, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public ItemReader<StockPriceDTO> StockReader(StockAssetService stockAssetService) throws IOException {
        return new RestStockItemReader(stockAssetService);
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
    public Step stockStep1 (StockAssetService stockAssetService, JpaItemWriter<StockPriceDTO> writer) throws IOException {
        return stepBuilderFactory.get("stockStep1")
                .<StockPriceDTO, StockPriceDTO> chunk(1)
                .reader(StockReader(stockAssetService))
                .processor(StockProcessor())
                .writer(writer)
                .build();
    }

}
