package com.sg.orderbook.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Trade {
    String TradeID;
    LocalDateTime ExecutionTime;
    int Quantity;
    BigDecimal Price;

    public String getTradeID() {
        return TradeID;
    }

    public void setTradeID(String tradeID) {
        TradeID = tradeID;
    }

    public LocalDateTime getExecutionTime() {
        return ExecutionTime;
    }

    public void setExecutionTime(LocalDateTime executionTime) {
        ExecutionTime = executionTime;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trade trade = (Trade) o;
        return Objects.equals(TradeID, trade.TradeID) &&
                Objects.equals(ExecutionTime, trade.ExecutionTime) &&
                Objects.equals(Quantity, trade.Quantity) &&
                Objects.equals(Price, trade.Price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(TradeID, ExecutionTime, Quantity, Price);
    }

    @Override
    public String toString() {
        return "Trade{" +
                "TradeID='" + TradeID + '\'' +
                ", ExecutionTime=" + ExecutionTime +
                ", Quantity=" + Quantity +
                ", Price=" + Price +
                '}';
    }
}
