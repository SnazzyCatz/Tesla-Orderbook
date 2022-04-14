package com.sg.orderbook.dao;

import com.sg.orderbook.dto.Order;
import com.sg.orderbook.dto.Trade;

import java.io.IOException;
import java.util.*;

public interface OrderBookDao {
    List<Order> GetBuyOrders();
    List<Order> GetSellOrders();
    Boolean addTrade(Trade trade);
    Trade MatchOrders();
    String MatchAllOrders();
    List<Object> OrderBookStats();
    Trade GetTradeByID(String ID);
    List<Trade> TradesByExecutionTime();
    Order addBuyOrder(Order buyOrder) throws OrderDaoException, IOException, IOException;
    Order addSellOrder(Order sellOrder) throws OrderDaoException, IOException;
    Order editOrder(List<String> orderInfo, String buyOrSell);
}
