package com.klur.stock.batch.processor;

import com.klur.stock.backend.entity.StockPriceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class StockItemProcessor implements ItemProcessor<StockPriceDTO, StockPriceDTO> {

    private static final Logger log = LoggerFactory.getLogger(StockItemProcessor.class);

    @Override
    public StockPriceDTO process(StockPriceDTO stockPriceDTO) throws Exception {

        final String symbol = stockPriceDTO.getSymbol().toUpperCase();
        final String name = stockPriceDTO.getName().toUpperCase();
        final String currency = stockPriceDTO.getCurrency().toUpperCase();
        final String stockExchange = stockPriceDTO.getStockExchange().toUpperCase();

        final StockPriceDTO transformedStock = StockPriceDTO.builder()
                .symbol(symbol)
                .name(name)
                .currency(currency)
                .stockExchange(stockExchange)
                .ask(stockPriceDTO.getAsk())
                .bid(stockPriceDTO.getBid())
                .build();

        log.info("Converting (" + stockPriceDTO + ") into (" + transformedStock + ")");

        return transformedStock;
    }
}
