package com.klur.stock.backend.repository;

import com.klur.stock.backend.entity.StockPriceDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepository extends JpaRepository<StockPriceDTO, Long> {
}
