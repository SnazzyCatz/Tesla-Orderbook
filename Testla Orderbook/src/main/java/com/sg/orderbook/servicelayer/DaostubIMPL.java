/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.orderbook.servicelayer;

import com.sg.orderbook.dao.OrderBookDao;
import com.sg.orderbook.dto.Order;
import com.sg.orderbook.dto.Trade;
import com.sg.orderbook.dao.OrderBookDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DaostubIMPL implements OrderBookDao {
    
    private Order onlyOrder;
    
    public DaostubIMPL(){
        
        onlyOrder = new Order();
        onlyOrder.setOrderId("1");
        onlyOrder.setPrice(new BigDecimal("190.57"));
        onlyOrder.setQuantity(34);
        
        
    }
    
    public DaostubIMPL(Order order){
        this.onlyOrder = order;
    }

    public void GenerateOrderBook() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> GetBuyOrders() {
        List<Order> BUYorderlist = new ArrayList<>();
        BUYorderlist.add(onlyOrder);
        return BUYorderlist;
        
    }

    @Override
    public List<Order> GetSellOrders() {
        List<Order> SELLorderlist = new ArrayList<>();
        SELLorderlist.add(onlyOrder);
        return SELLorderlist;
    }

    @Override
    public Boolean addTrade(Trade trade) {
        return true;
        
    }

    @Override
    public Trade MatchOrders() {
        return null;
       
    }

    @Override
    public String MatchAllOrders() {
        //do nothing
        return null;
    }

    @Override
    public List<Object> OrderBookStats() {
        return null;
        
    }

    @Override
    public Trade GetTradeByID(String ID) {
        return null;
       
    }

    @Override
    public List<Trade> TradesByExecutionTime() {
        return null;
       
    }

    @Override
    public Order addBuyOrder(Order buyOrder) {
        return null;
        
    }

    @Override
    public Order addSellOrder(Order buyOrder) {
        return null;
        
    }

    @Override
    public Order editOrder(List<String> orderInfo, String buyOrSell) {
        return null;
        
    }
    
}
