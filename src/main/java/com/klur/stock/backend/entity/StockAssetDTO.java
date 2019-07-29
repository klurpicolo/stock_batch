package com.klur.stock.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name="STOCK_LIST")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockAssetDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stock_id;
    private String symbol;

}
