package com.klur.stock.batch.reader;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

public class StockRest {

    public static void main(String args[] ){
        testOneStockGet();
    }

    private static void testOneStockGet(){
        try {
            Stock stock;
            stock = YahooFinance.get("CK.BK");
            BigDecimal price = stock.getQuote().getPrice();
            BigDecimal change = stock.getQuote().getChangeInPercent();
            BigDecimal peg = stock.getStats().getPeg();
            BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

            stock.print();
            System.out.println(stock.getQuote().toString());
//            System.out.println(stock.getQuote().getTimeZone().toString());
            Calendar calendar = stock.getQuote().getLastTradeTime();
            calendar.setTimeZone(stock.getQuote().getTimeZone());
            System.out.println(calendar.getTime());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testStocksGet(){
        String[] symbols = new String[]{"SCC.BK","SCB.BK","PTT.BK","BBL.BK"};
        try {
            Map<String, Stock> symbolStockMap = YahooFinance.get(symbols);
            Stock SCC = symbolStockMap.get("SCC.BK");
            Stock SCB = symbolStockMap.get("SCB.BK");

            SCC.print();
            SCB.print();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
