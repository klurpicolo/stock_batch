package com.klur.stock.backend.service;

import com.klur.stock.backend.entity.StockPriceDTO;
import com.klur.stock.backend.repository.StockPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockPriceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceService.class);

    @Autowired
    private StockPriceRepository stockPriceRepository;

    public List<StockPriceDTO> getAllStockPrice(){
        return stockPriceRepository.findAll();
    }
}
