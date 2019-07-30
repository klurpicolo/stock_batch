package com.klur.stock.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Data
@Builder
@Entity
@Table(name="STOCK")
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
    private Calendar lastTradeTime;
}
