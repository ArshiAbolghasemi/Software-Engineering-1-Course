package ir.ramtung.tinyme.domain;

import ir.ramtung.tinyme.domain.entity.*;
import ir.ramtung.tinyme.domain.service.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext
public class BrokerCreditTest {
    private Security security;
    private Broker buyerBroker;
    private Broker sellerBroker;
    private Shareholder sellerShareholder;

    private Shareholder buyerShareholder;
    private OrderBook orderBook;
    @Autowired
    private Matcher matcher;

    @BeforeEach
    void setupOrderBook() {
        security = Security.builder().build();
        sellerBroker = Broker.builder().credit(0).build();
        buyerBroker = Broker.builder().credit(100_000_000L).build();
        sellerShareholder = Shareholder.builder().build();
        sellerShareholder.incPosition(security, 100_000);
        buyerShareholder = Shareholder.builder().build();
        buyerShareholder.incPosition(security, 100_000);
        orderBook = security.getOrderBook();
        List<Order> orders = Arrays.asList(
                new Order(1, security, Side.BUY, 304, 15700, buyerBroker, buyerShareholder),
                new Order(2, security, Side.BUY, 43, 15500, buyerBroker, buyerShareholder),
                new Order(3, security, Side.BUY, 445, 15450, buyerBroker, buyerShareholder),
                new Order(4, security, Side.BUY, 526, 15450, buyerBroker, buyerShareholder),
                new Order(5, security, Side.BUY, 1000, 15400, buyerBroker, buyerShareholder),
                new Order(6, security, Side.SELL, 350, 15800, sellerBroker, sellerShareholder),
                new Order(7, security, Side.SELL, 285, 15810, sellerBroker, sellerShareholder),
                new Order(8, security, Side.SELL, 800, 15810, sellerBroker, sellerShareholder),
                new Order(9, security, Side.SELL, 340, 15820, sellerBroker, sellerShareholder),
                new Order(10, security, Side.SELL, 65, 15820, sellerBroker, sellerShareholder)
        );
        orders.forEach(order -> orderBook.enqueue(order));
    }

    @Test
    void new_buy_order_matches_with_entire_sell_queue_with_remain_quantity() {
        Order newOrder = new Order(11, security, Side.BUY, 2000, 15820, buyerBroker,
                buyerShareholder);
        MatchResult result = matcher.execute(newOrder);
        Order remainder = result.remainder();
        assertThat(result.trades().size()).isEqualTo(5);
        assertThat(remainder.getOrderId()).isEqualTo(11);
        assertThat(remainder.getQuantity()).isEqualTo(160);
        assertThat(remainder.getPrice()).isEqualTo(15820);
        assertThat(remainder.getStatus()).isEqualTo(OrderStatus.QUEUED);

        assertThat(buyerBroker.getCredit()).isEqualTo(68_377_850L);
        assertThat(sellerBroker.getCredit()).isEqualTo(29_090_950L);

        assertThat(security.getOrderBook().getBuyQueue()).contains(remainder);
        assertThat(security.getOrderBook().getSellQueue()).isEmpty();

        assertThat(buyerShareholder.getPositions().get(security)).isEqualTo(101_840);
        assertThat(sellerShareholder.getPositions().get(security)).isEqualTo(98_160);
    }

    @Test
    void new_buy_order_matches_partially_with_seller_orders() {
        Order newOrder = new Order(11, security, Side.BUY, 1000, 15810, buyerBroker,
                buyerShareholder);

        MatchResult result = matcher.execute(newOrder);
        LinkedList<Trade> trades = result.trades();
        assertThat(trades.size()).isEqualTo(3);
        assertThat(trades.getLast().getQuantity()).isEqualTo(365);

        Order remainder = result.remainder();
        assertThat(remainder.getQuantity()).isEqualTo(0);
        assertThat(remainder.getPrice()).isEqualTo(15810);
        assertThat(remainder.getStatus()).isEqualTo(OrderStatus.NEW);

        assertThat(buyerBroker.getCredit()).isEqualTo(84_193_500L);
        assertThat(sellerBroker.getCredit()).isEqualTo(15_806_500L);

        assertThat(buyerShareholder.getPositions().get(security)).isEqualTo(101_000);
        assertThat(sellerShareholder.getPositions().get(security)).isEqualTo(99_000);
    }

    @Test
    void new_order_not_match_any_sell_order() {
        Order newOrder = new Order(11, security, Side.BUY, 1000, 10_000, buyerBroker,
                buyerShareholder);
        MatchResult result = matcher.execute(newOrder);
        assertThat(result.trades()).isEmpty();
        assertThat(result.remainder()).isEqualTo(newOrder);

        assertThat(buyerBroker.getCredit()).isEqualTo(90_000_000L);
        assertThat(sellerBroker.getCredit()).isEqualTo(0);

        assertThat(buyerShareholder.getPositions().get(security)).isEqualTo(100_000);
        assertThat(sellerShareholder.getPositions().get(security)).isEqualTo(100_000);
    }

}
