package com.sg.orderbook.controller;

import com.sg.orderbook.dao.OrderBookDao;
import com.sg.orderbook.dao.OrderBookDaoImpl;
import com.sg.orderbook.dao.OrderDaoException;
import com.sg.orderbook.dto.Order;
import com.sg.orderbook.servicelayer.DataValidationException;
import com.sg.orderbook.servicelayer.DuplicateIDexception;
import com.sg.orderbook.servicelayer.ServiceLayer;
import com.sg.orderbook.servicelayer.ServiceLayerImpl;
import com.sg.orderbook.ui.UserIO;
import com.sg.orderbook.ui.UserIOImpl;
import com.sg.orderbook.ui.OrderBookView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class OrderBookController {

    private UserIO io = new UserIOImpl();
    private OrderBookView view;
    private ServiceLayer service;

    @Autowired
    public OrderBookController(ServiceLayer service, OrderBookView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    viewOrderBook();
                    break;
                case 2:
                    matchSingleOrder();
                    break;
                case 3:
                    matchAllOrders();
                    break;
                case 4:
                    viewSingleTrade();
                    break;
                case 5:
                    viewTradesByExecTime();
                    break;
                case 6:
                    addOrder();
                    break;
                case 7:
                    editOrder();
                    break;
                case 8:
                    keepGoing = false;
                    break;
                default:
                    io.print("UNKNOWN COMMAND");
            }
        }
        io.print("GOOD BYE");

    }

    private void viewOrderBook() {
        boolean keepgoing = true;
        while(keepgoing) {
            String pageNumber = view.getPageNumber();
            if(pageNumber.equals("e")) { break; }
            boolean isValid = true;
            for(int i = 0; i < pageNumber.length(); i++) {
                if(!Character.isDigit(pageNumber.charAt(i))) {
                    isValid = false;
                    break;
                }
            }
            double lastPage = Math.max(service.GetBuyOrders().size(), service.GetSellOrders().size());
            lastPage = (int) Math.ceil(lastPage/5);
            if(Integer.parseInt(pageNumber) > lastPage) { isValid = false; }
            if(isValid) {
                view.viewOrderBook((ArrayList<Order>) service.GetBuyOrders(), (ArrayList<Order>) service.GetSellOrders(), Integer.parseInt(pageNumber));
            }
            else {
                view.invalidInput();
            }
        }
        //view.viewOrderBook((ArrayList<Order>)service.GetBuyOrders(), (ArrayList<Order>)service.GetSellOrders(), 0);

    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void matchSingleOrder() {
        view.MatchOneOrderSuccess(true);

        view.DisplayTradeInfo(service.MatchOrders());
        
        view.displayStats();

        view.orderbookstats(service.OrderBookStats());
    }

    private void matchAllOrders() {
        view.displayMatchingAllOrders();
        String CreatedTradeRange = service.MatchAllOrders();
        view.DisplayCreatedTradeRange(CreatedTradeRange);
        view.orderbookstats(service.OrderBookStats());
    }

    private void viewSingleTrade() {
        String tradeID = view.GetTradeID();
        view.DisplayTradeInfo(service.GetTradeByID(tradeID));
    }

    private void viewTradesByExecTime() {
        
        view.DisplayTradesByExecutionTime(service.TradesByExecutionTime());
    }

    private void addOrder() {
        try {
            String choice = view.buyOrSellOrder();
            Order order = view.getOrderInfo();
            if (choice.charAt(0) == 'B') {
                service.addBuyOrder(order);
                System.out.println(order.toString());
            } else if (choice.charAt(0) == 'S') {
                service.addSellOrder(order);
                System.out.println(order.toString());
            } else {
                view.invalidOrderInput();
            }
        } catch(DataValidationException | DuplicateIDexception e) {
            view.invalidOrderInput();
            addOrder();
        } catch (OrderDaoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editOrder()  {
        try {
            String choice = view.buyOrSellOrder();
            List<String> orderInfo = view.getEditOrderInfo();
            Order order = service.editOrder(orderInfo, choice);
        } catch(DataValidationException | DuplicateIDexception e) {
            view.invalidOrderInput();
            editOrder();
        }
    }
}

