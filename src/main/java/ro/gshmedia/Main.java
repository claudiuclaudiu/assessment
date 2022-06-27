package ro.gshmedia;

import ro.gshmedia.data.dao.ProductDao;
import ro.gshmedia.messaging.client.ClientFactory;
import ro.gshmedia.service.OrderService;
import ro.gshmedia.service.ProductService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import ro.gshmedia.xml.parser.StockParser;

public class Main {

    public static void main (String[] args) throws IOException, TimeoutException {

        ProductService productService = new ProductService();
        ClientFactory clientFactory = new ClientFactory();
        OrderService orderService = new OrderService(clientFactory);
        productService.initProductStocks("stock1.xml");
        orderService.processOrder();
        clientFactory.closeConnection();


    }

}
