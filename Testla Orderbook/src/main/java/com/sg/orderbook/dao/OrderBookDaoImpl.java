package com.sg.orderbook.dao;

import com.sg.orderbook.dto.Order;
import com.sg.orderbook.dto.Trade;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OrderBookDaoImpl implements OrderBookDao {
    
    public static final String ORDER_FILE = "order_list.txt";
    public static final String DELIMITER2 = "\\|";
    
    private static final String DELIMITER = "|";
    private List<Order> BuyOrders = new ArrayList<>();
    private List<Order> SellOrders = new ArrayList<>();
    private List<Trade> Trades = new ArrayList<>();
    private int tradeIdCounter = 0;
    private int buyOrderIdCounter = 0;
    private int sellOrderIdCounter = 0;
    private String[] delimiters = new String[]{";","|","^"};
    private String filepath = "D:\\Java Code\\Tesla Order Book\\order_list.txt";

    public OrderBookDaoImpl() {
        //GenerateOrderBook();
        loadOrders();
    }

    private void GenerateOrderBook() {
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            Order BuyOrder = new Order();
            Order SellOrder = new Order();
            BuyOrder.setOrderId("BORD" + buyOrderIdCounter);
            SellOrder.setOrderId("SORD" + sellOrderIdCounter);
            BuyOrder.setPrice(new BigDecimal(Double.toString(rand.nextDouble() + 190)).setScale(2, RoundingMode.HALF_UP));
            SellOrder.setPrice(new BigDecimal(Double.toString(rand.nextDouble() + 190)).setScale(2, RoundingMode.HALF_UP));
            BuyOrder.setQuantity(rand.nextInt(30) + 20);
            SellOrder.setQuantity(rand.nextInt(30) + 20);
            BuyOrders.add(BuyOrder);
            SellOrders.add(SellOrder);
            buyOrderIdCounter++;
            sellOrderIdCounter++;
        }
    }

    public List<Order> GetBuyOrders() {
        SortList();
        return BuyOrders;
    }

    public List<Order> GetSellOrders() {
        SortList();
        return SellOrders;
    }

//    public Boolean addOrder(Order order) {
//        return BuyOrders.add(order);
//    }

    public Boolean addTrade(Trade trade) {
        return Trades.add(trade);
    }

    public Order addBuyOrder(Order buyOrder) throws OrderDaoException, IOException {
        buyOrder.setOrderId("BORD" + buyOrderIdCounter);
        buyOrderIdCounter++;
        BuyOrders.add(buyOrder);
        writeOrder(Stream.concat(BuyOrders.stream(), SellOrders.stream()).collect(Collectors.toList()));
        return buyOrder;
    }

    public Order addSellOrder(Order sellOrder) throws OrderDaoException, IOException {
        sellOrder.setOrderId("BORD" + sellOrderIdCounter);
        sellOrderIdCounter++;
        SellOrders.add(sellOrder);
        writeOrder(Stream.concat(BuyOrders.stream(), SellOrders.stream()).collect(Collectors.toList()));
        return sellOrder;
    }

    public Order editOrder(List<String> orderInfo, String buyOrSell) {
        if(buyOrSell.charAt(0) == 'B') {
            for (Order order : BuyOrders) {
                if (order.getOrderId().equals(orderInfo.get(0))) {
                    order.setPrice(new BigDecimal(orderInfo.get(1)));
                    order.setQuantity(Integer.parseInt(orderInfo.get(2)));
                    return order;
                }
            }
        }
        else if(buyOrSell.charAt(0) == 'S') {
            for(Order order : SellOrders) {
                if(order.getOrderId().equals(orderInfo.get(0))) {
                    order.setPrice(new BigDecimal(orderInfo.get(1)));
                    order.setQuantity(Integer.parseInt(orderInfo.get(2)));
                    return order;
                }
            }
        }
        else {
            return null;
        }
        return null;
    }

    public Trade MatchOrders() {
        SortList();
        if (BuyOrders.isEmpty() || SellOrders.isEmpty()) {
            return null;
        }
        LocalDateTime ld = LocalDateTime.now();
        Trade trade = new Trade();
        trade.setTradeID("" + tradeIdCounter);
        trade.setQuantity(BuyOrders.get(0).getQuantity());
        trade.setExecutionTime(ld);
        if (BuyOrders.get(0).getQuantity() < SellOrders.get(0).getQuantity()) {
            trade.setPrice(SellOrders.get(0).getPrice());
            SellOrders.get(0).setQuantity(SellOrders.get(0).getQuantity() - BuyOrders.get(0).getQuantity());
            BuyOrders.remove(0);
            tradeIdCounter++;
            Trades.add(trade);
            return trade;
        }
        String price = "0";
        int BuyerQuantity = BuyOrders.get(0).getQuantity();
        while (!SellOrders.isEmpty() && BuyOrders.get(0).getQuantity() > 0) {
            BigDecimal currentPrice = new BigDecimal(price);
            BigDecimal sellerQuantity = new BigDecimal(Integer.toString(SellOrders.get(0).getQuantity()));
            BigDecimal sellPrice = sellerQuantity.multiply(SellOrders.get(0).getPrice());
            BigDecimal totalPrice = currentPrice.add(sellPrice);
            price = totalPrice.toString();
            trade.setPrice(SellOrders.get(0).getPrice());
            BuyOrders.get(0).setQuantity(BuyOrders.get(0).getQuantity() - SellOrders.get(0).getQuantity());
            SellOrders.remove(0);
            if (!SellOrders.isEmpty() && BuyOrders.get(0).getQuantity() < SellOrders.get(0).getQuantity()) {
                BigDecimal addedPrice = new BigDecimal(price);
                BigDecimal remainingQuantity = new BigDecimal(Integer.toString(BuyOrders.get(0).getQuantity()));
                BigDecimal resultPrice = remainingQuantity.multiply(SellOrders.get(0).getPrice());
                BigDecimal finalPrice = resultPrice.add(addedPrice);
                BigDecimal averagePrice = finalPrice.divide(new BigDecimal(Integer.toString(BuyerQuantity)), 2, RoundingMode.HALF_UP);
                trade.setPrice(averagePrice);
                SellOrders.get(0).setQuantity(SellOrders.get(0).getQuantity() - BuyOrders.get(0).getQuantity());
                BuyOrders.remove(0);
                tradeIdCounter++;
                break;
            }
        }
        Trades.add(trade);
        return trade;
    }

    public String MatchAllOrders() {
        int CurrentTradeNo = Trades.size();
        int count = 0;
        while (!BuyOrders.isEmpty() && !SellOrders.isEmpty()) {
            count++;
            Trade trade = MatchOrders();
        }
        int sum = CurrentTradeNo + count;
        return (CurrentTradeNo + "-" + sum);
    }

    public List<Object> OrderBookStats() {
        List<Object> stats = new ArrayList<>();
        int BuyQuantity = 0;
        int SellQuantity = 0;
        String TotalBuyPrice = "0";
        String TotalSellPrice = "0";
        for (Order order : BuyOrders) {
            BuyQuantity += order.getQuantity();
            BigDecimal CurrentBuyPrice = new BigDecimal(TotalBuyPrice);
            BigDecimal CurrentOrderPrice = order.getPrice().multiply(new BigDecimal(Integer.toString(order.getQuantity())));
            BigDecimal NewBuyPrice = CurrentBuyPrice.add(CurrentOrderPrice);
            TotalBuyPrice = NewBuyPrice.toString();
        }
        for (Order order : SellOrders) {
            SellQuantity += order.getQuantity();
            BigDecimal CurrentSellPrice = new BigDecimal(TotalSellPrice);
            BigDecimal CurrentOrderPrice = order.getPrice().multiply(new BigDecimal(Integer.toString(order.getQuantity())));
            BigDecimal NewSellPrice = CurrentSellPrice.add(CurrentOrderPrice);
            TotalSellPrice = NewSellPrice.toString();
        }
        BigDecimal AverageBuyPrice;
        BigDecimal AverageSellPrice;
        if (BuyQuantity > 0) {
            AverageBuyPrice = new BigDecimal(TotalBuyPrice).divide(new BigDecimal(Integer.toString(BuyQuantity)), 2, RoundingMode.HALF_UP);
        } else {
            AverageBuyPrice = new BigDecimal("0");
        }
        if (SellQuantity > 0) {
            AverageSellPrice = new BigDecimal(TotalSellPrice).divide(new BigDecimal(Integer.toString(SellQuantity)), 2, RoundingMode.HALF_UP);
        } else {
            AverageSellPrice = new BigDecimal("0");
        }
        stats.add(0, BuyOrders.size());
        stats.add(1, SellOrders.size());
        stats.add(2, BuyQuantity);
        stats.add(3, SellQuantity);
        stats.add(4, AverageBuyPrice);
        stats.add(5, AverageSellPrice);

        return stats;
    }

    public Trade GetTradeByID(String ID) {
        for (Trade trade : Trades) {
            if (ID.equals(trade.getTradeID())) {
                return trade;
            }
        }
        return null;
    }

    public List<Trade> TradesByExecutionTime() {
        Trades.sort(Comparator.comparing(Trade::getExecutionTime));
        //BuyOrders.sort(Comparator.comparing(Order::getPrice));
        return Trades;
    }

    public List<Trade> TradesByQuantity() {
        Trades.sort(Comparator.comparing(Trade::getQuantity));
        return Trades;
    }

    public List<Trade> TradesByPrice() {
        Trades.sort(Comparator.comparing(Trade::getPrice));
        return Trades;
    }

    private void SortList() {
        //Collections.sort(OrderList, new CustomOrderComparator());
        //Collections.sort(BuyOrders, Collections.reverseOrder());
        BuyOrders.sort(Comparator.comparing(Order::getPrice).reversed());
        SellOrders.sort(Comparator.comparing(Order::getPrice));
    }
    
        private String marshallOrders(Order anOrder){
        
//        8=FIX4.2|9=0132|35=D|57=ADMIN|34=2|49=TESTA|56=TESTB|
//        52=20100315-13:45:28|55=BARC|40=2|38=1000|21=2|11=OrderNumber0|
//        60=2010031517:45:20|54=1|44=110.5|10=9

        LocalDateTime dt = LocalDateTime.now();

        String orderAsText = "8=FIX4.2|9=0132|35=D|57=ADMIN|34=2|49=TESTA|56=TESTB|52=";
        
        
        orderAsText+=dt+ DELIMITER;       
        //anOrder.getOrderID()+DELIMITER;
        orderAsText+="55=BARC|40=2|";
        orderAsText+="38="+anOrder.getQuantity()+DELIMITER;
        orderAsText+="21=2|";
        orderAsText+="11="+anOrder.getOrderId()+DELIMITER;
        orderAsText+="60="+dt+DELIMITER;
        
        if(anOrder.getOrderId().charAt(0)=='B'){
             orderAsText+="54="+1+DELIMITER;
        }
        
        if(anOrder.getOrderId().charAt(0)=='S'){
            orderAsText+="54="+2+DELIMITER;
        }
        
        orderAsText+="44="+anOrder.getPrice()+DELIMITER;
        orderAsText+="10=9";
        
        return orderAsText;
       
    }
    
    private void writeOrder(List<Order> list) throws OrderDaoException, IOException {
        
        PrintWriter scanner;
        try {
            scanner = new PrintWriter(new FileWriter(ORDER_FILE));
        } catch (FileNotFoundException e) {
            throw new OrderDaoException( "-_- Could not load order data into memory.", e);
        }
        String currentLine;
        
        for(Order order : list){
            scanner.println(marshallOrders(order));
        }
        scanner.close();
    }

    private Order unmarshallOrder(String orderAsText) {
        Order order = new Order();
        boolean nonEmptyQuantity = false;
        String delimiter;
        if(orderAsText.contains(";")) {
            delimiter = ";";
        }
        else if(orderAsText.contains("|")) {
            delimiter = "\\|";
        }
        else if(orderAsText.contains("^")) {
            delimiter = "\\^";
        }
        else {
            delimiter = null;
        }
        if(delimiter != null) {
            String[] orderArr = orderAsText.split(delimiter);
            for(int i = 0; i< orderArr.length; i++) {
                //Tag 11 is for order ID
                if(orderArr[i].startsWith("11")) {
                    order.setOrderId(orderArr[i].substring(3));
                }
                //Tag 38 is for quantity
                if(orderArr[i].startsWith("38")) {
                    order.setQuantity(Integer.parseInt(orderArr[i].substring(3)));
                    nonEmptyQuantity = true;
                }
                //Tag 44 is for price
                if(orderArr[i].startsWith("44")) {
                    order.setPrice(new BigDecimal(orderArr[i].substring(3)));
                }
                //Tag 54 is for buy or sell side
//                if(orderArr[i].substring(0,2).equals("54")) {
//                    order
//                }
            }
        }
        if(order.getOrderId() != null && nonEmptyQuantity && order.getPrice() != null) {
            return order;
        }
        else {
            return null;
        }
    }

    private void loadOrders() {
        Scanner scanner;
         try {
             scanner = new Scanner(new BufferedReader(new FileReader(filepath)));
             Order currentOrder;

             while(scanner.hasNextLine()) {
                 currentOrder = unmarshallOrder(scanner.nextLine());
                 if(currentOrder == null) {
                     continue;
                 }
                 else if(currentOrder.getOrderId().contains("BORD")) {
                     BuyOrders.add(currentOrder);
                 }
                 else {
                     SellOrders.add(currentOrder);
                 }
             }
             scanner.close();
         } catch (FileNotFoundException e) {
             System.out.println(e.getMessage());
         }
    }
}
