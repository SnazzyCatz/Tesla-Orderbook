package com.sg.orderbook.ui;

import com.sg.orderbook.dto.Order;
import com.sg.orderbook.dto.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class OrderBookView {
    private UserIO io;

    @Autowired
    public OrderBookView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("1. View Order Book");
        io.print("2. Match One Order");
        io.print("3. Match All Orders");
        io.print("4. Search For Trade By ID");
        io.print("5. View All Trades (Sorted By Execution Time)");
        io.print("6. Add Order");
        io.print("7. Edit Order");
        io.print("8. Exit");

        return io.readInt("Please select from the above choices:", 1, 8);
    }

    public void displayAddOrderBanner() {
        io.print("=== Order Book ===");
    }

    public void viewOrderBook(ArrayList<Order> BuyOrders, ArrayList<Order> SellOrders, int PageNumber) {
        int count = 0;
        double maxLength = Math.max(BuyOrders.size(), SellOrders.size());
        ArrayList<ArrayList<Order>> BuyOrderPages = new ArrayList<>();
        ArrayList<ArrayList<Order>> SellOrderPages = new ArrayList<>();
        for (int i = 0; i < Math.ceil(maxLength / 5); i++) {
            ArrayList<Order> TempBuyOrders = new ArrayList<>();
            ArrayList<Order> TempSellOrders = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                if (count < BuyOrders.size()) {
                    //TempBuyOrders.add(j,BuyOrders.get(count));
                    boolean check = TempBuyOrders.add(BuyOrders.get(count));
                }
                if (count < SellOrders.size()) {
                    TempSellOrders.add(j, SellOrders.get(count));
                }
                count++;
            }
            BuyOrderPages.add(i, TempBuyOrders);
            SellOrderPages.add(i, TempSellOrders);
        }

        io.print("BUY ORDERS:");
        for (int i = 0; i < BuyOrderPages.get(PageNumber).size(); i++) {
            System.out.println(BuyOrderPages.get(PageNumber).get(i).getOrderId() + "," + BuyOrderPages.get(PageNumber).get(i).getPrice() + "," + BuyOrderPages.get(PageNumber).get(i).getQuantity());
        }
        io.print("---------------------------\n" + "SELL ORDERS:");
        for(int i = 0; i < SellOrderPages.get(PageNumber).size(); i++) {
            io.print(SellOrderPages.get(PageNumber).get(i).getOrderId() + "," + SellOrderPages.get(PageNumber).get(i).getPrice() + "," + SellOrderPages.get(PageNumber).get(i).getQuantity());
        }
        //io.print("Press " + ((int) Math.ceil(maxLength / 5) + 1) + " to exit");
        io.print("There Are " + (int) Math.ceil(maxLength/5) + " Pages\n");
        io.print("Press e to exit");
    }

    public String getPageNumber() {
        String input = io.readString("Please Enter A Page Number:");
        if(input.equals("e") || input.equals("E")) {
            return "e";
        }
        return input;
    }

    public void invalidInput() {
        io.print("Invalid Input");
    }

    public void MatchOneOrderSuccess(boolean success) {
        if (success) {
            io.print("Order Match Was Successful");
        } else {
            io.print("Order Match Failed");
        }
    }

    public void MatchAllOrdersSuccess(boolean success) {
        if (success) {
            io.print("All Orders Matched Successfully");
        } else {
            io.print("All Orders Matched Failed");
        }
    }

    public String GetTradeID() {
        return io.readString("Enter The Trade ID You'd Like To Search");
    }

    public void displayOneOrder() {
        io.print("=== Order ===");
    }

    public void DisplayTradeInfo(Trade trade) {
        io.print("Trade:" + trade.getTradeID());
        io.print("Price:" + trade.getPrice());
        io.print("Quantity:" + trade.getQuantity());
        io.print("Execution Time:" + trade.getExecutionTime());
    }

    public void displayOrdersBanner() {
        io.print("=== All Orders ===");
    }
    
    public void orderbookstats(List<Object> OrderBookStats) {
        
        List<Object> stats = OrderBookStats;
        
//        stats.add(0, BuyOrders.size());
//        stats.add(1, SellOrders.size());
//        stats.add(2, BuyQuantity);
//        stats.add(3, SellQuantity);
//        stats.add(4, AverageBuyPrice);
//        stats.add(5, AverageSellPrice);

    io.print("Volume of Bids: " + stats.get(0)+", Volume of Ask: "+stats.get(1)+", Buy quantity: "+stats.get(2)+ ", Sell quantity: "+stats.get(3)+", Average bid price: "+stats.get(4)+", Average ask price: "+stats.get(5));
        
    }

    public void displayStats() {
        
        io.print("==== Statistics for the day ====");
    }

    public void displayMatchingAllOrders() {
         io.print("==== Matching All Orders ====");
    }

    public void DisplayTradesByExecutionTime(List<Trade> SortedTradeList) {
        for (Trade trade : SortedTradeList) {
            io.print("Trade ID:" + trade.getTradeID());
            io.print("Trade Price:" + trade.getPrice());
            io.print("Trade Quantity:" + trade.getQuantity());
            io.print("Trade Execution Time:" + trade.getExecutionTime());
            io.print("------------------------------");
        }
    }

    public void DisplayCreatedTradeRange(String range) {
        io.print("Here is the range of trades created: " + range);
    }

    public Order getOrderInfo() {
        Order order = new Order();
        String askPrice = io.readString("Enter your ask price: ");
        order.setPrice(new BigDecimal(askPrice));
        int quantity = io.readInt("Enter the quantity of shares you'd like to trade: ");
        order.setQuantity(quantity);
        return order;
    }

    public String buyOrSellOrder() {
        return io.readString("Would you like to buy or sell(B/S): ").toUpperCase();
    }

    public List<String> getEditOrderInfo() {
        List<String> orderInfo = new ArrayList<>();
        orderInfo.add(io.readString("Enter the ID of the order you'd like to edit: "));
        orderInfo.add(io.readString("Enter the price you'd like to change to: "));
        orderInfo.add(io.readString("Enter the quanity you'd like to change to: "));
        return orderInfo;
    }

    public void invalidOrderInput() {
        io.print("Invalid Order Input");
    }
    
        public void displayErrorMessage(String message) {
        io.print(message);
    }
}
