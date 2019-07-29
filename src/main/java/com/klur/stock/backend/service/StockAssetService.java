package com.klur.stock.backend.service;

import com.klur.stock.backend.entity.StockAssetDTO;
import com.klur.stock.backend.repository.StockAssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockAssetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockAssetService.class);

    @Autowired
    private StockAssetRepository stockAssetRepository;

    public List<StockAssetDTO> getAllStockAsset(){
        return stockAssetRepository.findAll();
    }


}
