package com.klur.stock.backend.repository;

import com.klur.stock.backend.entity.StockAssetDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface StockAssetRepository extends JpaRepository<StockAssetDTO, Long> {
}
