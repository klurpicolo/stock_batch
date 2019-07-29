package com.klur.stock.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name="stock")
@NoArgsConstructor
@AllArgsConstructor
public class StockPriceDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stock_id;
    private String symbol;
    private String name;
    private String currency;
    private String stockExchange;
    private BigDecimal ask;
    private BigDecimal bid;
}
