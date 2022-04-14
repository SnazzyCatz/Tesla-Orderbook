package com.sg.orderbook.servicelayer;

import com.sg.orderbook.dao.OrderBookDao;
import com.sg.orderbook.dao.OrderDaoException;
import com.sg.orderbook.dto.Order;
import com.sg.orderbook.dto.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Component
public class ServiceLayerImpl implements ServiceLayer {
    private OrderBookDao dao;

    @Autowired
    public ServiceLayerImpl(OrderBookDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Order> GetBuyOrders() {
        return dao.GetBuyOrders();

    }

    @Override
    public List<Order> GetSellOrders() {
        return dao.GetSellOrders();

    }

    @Override
    public Boolean addTrade(Trade trade) {

        return dao.addTrade(trade);

    }

    @Override
    public Trade MatchOrders() {

        return dao.MatchOrders();

    }

    @Override
    public String MatchAllOrders() {

        return dao.MatchAllOrders();

    }

    @Override
    public List<Object> OrderBookStats() {

        return dao.OrderBookStats();

    }

    @Override
    public Trade GetTradeByID(String ID) {

        return dao.GetTradeByID(ID);

    }

    @Override
    public List<Trade> TradesByExecutionTime() {

        return dao.TradesByExecutionTime();

    }

//    @Override
//    public Order editOrder(List<String> orderInfo, String buyOrSell) {
//        return dao.editOrder(orderInfo, buyOrSell);
//    }
//
//    @Override
//    public Order addSellOrder(Order sellOrder) {
//        return dao.addSellOrder(sellOrder);
//    }
//
//    @Override
//    public Order addBuyOrder(Order buyOrder) {
//        return dao.addBuyOrder(buyOrder);
//    }

     private void validateOrderData(Order order) throws DuplicateIDexception, DataValidationException {
        
        //there was another condition inside the if statement about checking for the existing order ID...
        
                if(order.getQuantity()<20
                   || order.getQuantity()>50
                   || order.getPrice().doubleValue()<190
                   || order.getPrice().doubleValue()>191){
            
            throw new DataValidationException("ERROR: The order price should range between $190 and $191, the quantity should range between 20 and 50");
        }

        
    }

    @Override
    public void addBuyOrder(Order buyOrder) throws DuplicateIDexception, DataValidationException, OrderDaoException, IOException, IOException, OrderDaoException {
        
//             if(dao.GetBuyOrders().get(0).getOrderID().equals(buyOrder.getOrderID())){
                
//                 throw new DuplicateIDexception("ERROR: Could not add order. Buy order "+buyOrder.getOrderID()+" already exists");
//             }
            
            validateOrderData(buyOrder);
            
            dao.addBuyOrder(buyOrder);
           
    }
    
    @Override
    public void addSellOrder(Order sellOrder) throws DuplicateIDexception, DataValidationException, OrderDaoException, IOException {
        
//             if(dao.GetSellOrders().get(0).getOrderID().equals(sellOrder.getOrderID())){
                
//                 throw new DuplicateIDexception("ERROR: Could not add order. Sell order "+sellOrder.getOrderID()+" already exists");
//             }
            
            validateOrderData(sellOrder);
            
            dao.addSellOrder(sellOrder);
       
    }

    @Override
    public Order editOrder(List<String> orderInfo, String buyOrSell) throws DataValidationException, DuplicateIDexception {
        
       Order editedOrder = new Order();
       
       editedOrder.setOrderId(orderInfo.get(0));
       editedOrder.setPrice(new BigDecimal(orderInfo.get(1)));
       editedOrder.setQuantity(Integer.parseInt(orderInfo.get(2)));
       
       validateOrderData(editedOrder);
       dao.editOrder(orderInfo, buyOrSell);
//       if(buyOrSell.charAt(0)=='B'){
//           dao.addBuyOrder(editedOrder);
//       }else if(buyOrSell.charAt(0)=='S'){
//           dao.addSellOrder(editedOrder);
//       }
        
        
        return editedOrder;
      
       
    }

    
}
