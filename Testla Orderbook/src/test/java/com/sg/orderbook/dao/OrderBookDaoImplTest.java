package com.sg.orderbook.dao;

import com.sg.orderbook.dto.Order;
import com.sg.orderbook.dto.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class OrderBookDaoImplTest {
    OrderBookDao dao = new OrderBookDaoImpl();

    public OrderBookDaoImplTest() {

    }
        // methods to be tested:
//    public List<Order> GetBuyOrders();
//    public List<Order> GetSellOrders();
//    public Boolean addTrade(Trade trade);
//    public Trade MatchOrders();
//    public void MatchAllOrders();
//    public List<Object> OrderBookStats();
//    public Trade GetTradeByID(String ID);
//    public List<Trade> TradesByExecutionTime();

    @BeforeEach
    public void setUp() {

    }
    @Test
    public void testGetBuyOrders() {
        //Arrange
        List<Order> buyorders;
        //Act
        buyorders = dao.GetBuyOrders();
        //Assert
        assertNotNull(buyorders, "Should not be null");
    }

    @Test
    public void testSellBuyOrders() {
        //Arrange
        List<Order> sellorders;
        //Act
        sellorders = dao.GetSellOrders();
        //Assert
        assertNotNull(sellorders, "Should not be null");
    }
    
         @Test
    public void testMatchOrders() {
        
        //Arrange
        Trade trade;
        
        //Act
        trade = dao.MatchOrders();
        
        //Assert
        assertNotNull(trade, "Trade should not be null");
        assertNotNull(trade.getPrice(), "Trade price should not null");
        assertNotNull(trade.getQuantity(), "Quantity should not null");
        assertNotNull(trade.getExecutionTime() , "Trade time should not null");
        
        
    }
    
    @Test
    public void testOrderBookStats() throws Exception{
        
        //Arrange
        List<Object> stats ;
        
        //Act
        stats = dao.OrderBookStats();
        
        
        //Assert
        assertNotNull(stats, "Stats should not be null");
        
    }
    
    @Test
    public void testGetTradesbyExecutionTime(){
        
        //Arrange
        List<Trade> trades;
        
        //Act
        trades = dao.TradesByExecutionTime();
        
        
        //Assert
        assertNotNull(trades, "Stats should not be null");
        
    }
    
        @Test
    public void testAddBuyOrder() throws Exception {
        
        Order bord1 = new Order();
        
        bord1.setOrderId("1");
        bord1.setPrice(new BigDecimal(190.00).setScale(2, RoundingMode.HALF_UP));
        bord1.setQuantity(10);
        
        dao.addBuyOrder(bord1);
        
        List<Order> orderss = dao.GetBuyOrders();
        

        assertTrue(orderss.contains(bord1));
    }
    
    @Test
    public void testAddSellOrder() throws Exception {
        
        Order sord1 = new Order();
        
        sord1.setOrderId("1");
        sord1.setPrice(new BigDecimal(190.00).setScale(2, RoundingMode.HALF_UP));
        sord1.setQuantity(10);
        
        dao.addSellOrder(sord1);
        
        List<Order> orderss = dao.GetSellOrders();
        

        assertTrue(orderss.contains(sord1));
    }
    
        @Test
    public void testEditOrder() throws Exception {
        
        //Arrange
        Order orderNew = new Order();
        orderNew.setOrderId("BORD1");
        orderNew.setPrice(new BigDecimal(190.23).setScale(2, RoundingMode.HALF_UP));
        orderNew.setQuantity(30);

        List<String> editInfo = new ArrayList<>();
        editInfo.add("BORD1");
        editInfo.add("190.44");
        editInfo.add("45");
        
        //Act
        dao.addBuyOrder(orderNew);
        dao.editOrder(editInfo, "B");
        Order orderCheck = null;
        for(Order order : dao.GetBuyOrders()){
            if(order.getOrderId().equals("BORD1")){
                orderCheck = order;
            }
        }
        
        //Assert
        
        assertNotNull(orderCheck, "This buy order should not be null");
        assertEquals(orderCheck.getPrice(), new BigDecimal(editInfo.get(1)), "The price should be 190.44");
        assertEquals(orderCheck.getQuantity(), Integer.parseInt(editInfo.get(2)), "The quantity should be 45");
    }
}
