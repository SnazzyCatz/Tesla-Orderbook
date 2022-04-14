import com.sg.orderbook.controller.OrderBookController;
import com.sg.orderbook.dao.OrderBookDaoImpl;
import com.sg.orderbook.dto.Order;
import com.sg.orderbook.dto.Trade;
import com.sg.orderbook.ui.OrderBookView;
import com.sg.orderbook.ui.UserIO;
import com.sg.orderbook.ui.UserIOImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.*;

public class App {
    public static void main(String[] args) {

//        UserIO io = new UserIOImpl();
//        OrderBookView view = new OrderBookView(io);
//        OrderBookDaoImpl dao = new OrderBookDaoImpl();
//        List<Order> Buys = dao.GetBuyOrders();
//        List<Order> Sells = dao.GetSellOrders();
//
//        for(Order order : Buys) {
//            System.out.println(order.toString());
//        }
//        System.out.println("------------------------------");
//        for(Order order : Sells) {
//            System.out.println(order.toString());
//        }
//        System.out.println("------------------------------");
//        dao.MatchOrders();
//        Order buy = new Order();
//        buy.setPrice(new BigDecimal("30"));
//        buy.setQuantity(30);
//        dao.addBuyOrder(buy);
//        dao.addSellOrder(buy);
//        List<String> info = view.getEditOrderInfo();
//        dao.editOrder(info, "S");
//        for(Order order : Buys) {
//            System.out.println(order.toString());
//        }
//        System.out.println("------------------------------");
//        for(Order order : Sells) {
//            System.out.println(order.toString());
//        }
//        dao.MatchOrders();
//        dao.MatchOrders();
//        dao.MatchOrders();
//        dao.MatchOrders();
//        dao.MatchOrders();
//
//        List<Trade> trades = dao.TradesByExecutionTime();
//        for(Trade trade : trades) {
//            System.out.println(trade.toString());
//        }
//        trade.toString();
//        Trade trade = dao.GetTradeByID("0");
//        System.out.println(trade.toString());

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.orderbook");
        appContext.refresh();

        OrderBookController controller = appContext.getBean("orderBookController", OrderBookController.class);
        controller.run();
    }
}
