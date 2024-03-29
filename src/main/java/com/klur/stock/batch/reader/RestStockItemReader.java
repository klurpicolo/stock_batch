package com.klur.stock.batch.reader;

import com.klur.stock.backend.entity.StockAssetDTO;
import com.klur.stock.backend.entity.StockPriceDTO;
import com.klur.stock.backend.service.StockAssetService;
import com.klur.stock.backend.service.StockPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RestStockItemReader implements ItemReader<StockPriceDTO> {

    private static final Logger log = LoggerFactory.getLogger(RestStockItemReader.class);
    private List<StockPriceDTO> stockList;
    private boolean isStockDataFetched = false;

//    @Autowired
    private final StockAssetService stockAssetService;

    public RestStockItemReader(StockAssetService stockAssetService){
        this.stockAssetService = stockAssetService;
    }

    @Override
    public StockPriceDTO read() throws Exception {
        StockPriceDTO result;

        if(!isStockDataFetched){
            this.stockList = fetchStockDataFromApi();
            isStockDataFetched = true;
        }

        if(stockList.isEmpty()){
            result = null;
            isStockDataFetched = false;
        } else{
            result = stockList.remove(0);
        }
        return result;

    }

    private List<StockPriceDTO> fetchStockDataFromApi() throws IOException {
//        String[] symbols = new String[]{"SCC.BK","SCB.BK","PTT.BK","BBL.BK"};

        List<StockAssetDTO> stockAssetList = stockAssetService.getAllStockAsset();
        List<StockPriceDTO> stockList = new ArrayList<>();
        log.info("Reader fetch assets : " + stockAssetList.size());

        if(stockAssetList.isEmpty()){
            return stockList;
        }

        List<String> symbolList = new ArrayList<>();
        stockAssetList.forEach(stock -> symbolList.add(stock.getSymbol()));
        String[] symbols = symbolList.toArray(new String[stockAssetList.size()]);

        try {
            Map<String, Stock> symbolStockMap = YahooFinance.get(symbols);
            symbolStockMap.forEach(
                    (symbol, stock) -> stockList.add(
                            StockPriceDTO.builder()
                            .name(stock.getName())
                            .symbol(stock.getSymbol())
                            .currency(stock.getCurrency())
                            .stockExchange(stock.getStockExchange())
                                    .ask(stock.getQuote().getAsk())
                                    .bid(stock.getQuote().getBid())
                                    .lastTradeTime(stock.getQuote().getLastTradeTime())
                            .build()
                    )
            );
        } catch (IOException e) {
            log.error("Error during get stock from API");
            e.printStackTrace();
        }
        return stockList;
    }
}
