package com.sg.orderbook.servicelayer;

import com.sg.orderbook.dao.OrderDaoException;
import com.sg.orderbook.dto.Order;
import com.sg.orderbook.dto.Trade;

import java.io.IOException;
import java.util.*;

public interface ServiceLayer {

    public List<Order> GetBuyOrders();

    public List<Order> GetSellOrders();

    public Boolean addTrade(Trade trade);

    public Trade MatchOrders();

    public String MatchAllOrders();

    public List<Object> OrderBookStats();

    public Trade GetTradeByID(String ID);

    public List<Trade> TradesByExecutionTime();

//    Order editOrder(List<String> orderInfo, String buyOrSell);
//
//    Order addSellOrder(Order sellOrder);
//
//    Order addBuyOrder(Order buyOrder);
  
    void addBuyOrder(Order buyOrder) throws DuplicateIDexception, DataValidationException, OrderDaoException, IOException, IOException, OrderDaoException;

    void addSellOrder(Order buyOrder) throws DuplicateIDexception, DataValidationException, OrderDaoException, IOException;

    Order editOrder(List<String> orderInfo, String buyOrSell) throws DataValidationException, DuplicateIDexception;
  
}
