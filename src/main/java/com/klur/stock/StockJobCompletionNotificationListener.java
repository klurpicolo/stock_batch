package com.klur.stock;

import com.klur.stock.backend.entity.StockPriceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

//@Component
public class StockJobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StockJobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");



            jdbcTemplate.query("SELECT symbol, name FROM stock",
//                    (rs, row) -> new StockPriceDTO(
//                            rs.getString(1),
//                            rs.getString(2),
//                            "",
//                            "")
                    (rs, row) -> StockPriceDTO.builder()
                    .symbol(rs.getString(1))
                    .name(rs.getString(2))
                    .build()
            ).forEach(stock -> log.info("Found <" + stock + "> in the database."));
        }
    }
}
