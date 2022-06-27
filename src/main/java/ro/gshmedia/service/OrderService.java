package ro.gshmedia.service;

import ro.gshmedia.data.dao.OrderDao;
import ro.gshmedia.data.dao.ProductDao;
import ro.gshmedia.data.dao.impl.OrderDaoImpl;
import ro.gshmedia.data.dao.impl.ProductDaoImpl;
import ro.gshmedia.data.model.Order;
import ro.gshmedia.data.model.Product;
import ro.gshmedia.data.model.Status;
import ro.gshmedia.messaging.client.ClientFactory;
import ro.gshmedia.messaging.consumer.OrderConsumer;
import ro.gshmedia.messaging.model.OrderRequest;
import ro.gshmedia.messaging.model.OrderResponse;
import ro.gshmedia.messaging.producer.OrderResponseProducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private OrderConsumer orderConsumer;
    private OrderDao orderDao = new OrderDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private OrderResponseProducer orderResponseProducer;

    public OrderService(ClientFactory clientFactory) {
        orderConsumer = new OrderConsumer(clientFactory.getChannel());
        orderResponseProducer = new OrderResponseProducer(clientFactory.getChannel());
    }

    public void processOrder() throws IOException {
        OrderRequest request = orderConsumer.getOrderFromQueue();
        List<Product> productList = new ArrayList<>();
        List<Product> productListBeforeUpdate = new ArrayList<>();
        Order order = new Order();
        order.setClient(request.getClientName());
        request.getItems().forEach(e -> {
            productList.add(productDao.updateProductById(e.getProduct_id(), e.getQuantity()));
            productListBeforeUpdate.add(productDao.getProduct(e.getProduct_id()));
        });
        order = orderDao.saveOrder(order);
        Order finalOrder = order;
        request.getItems().forEach(e -> {
            productListBeforeUpdate.forEach(p -> {
                if(p.getId() == e.getProduct_id())
                {
                    int stock = p.getStock() - e.getQuantity();
                    productDao.updateProductById(e.getProduct_id(), stock);
                    if (stock >= 0)
                    {
                        orderDao.updateOrderStatusById(finalOrder.getId(), Status.RESERVED);
                    } else {
                        orderDao.updateOrderStatusById(finalOrder.getId(), Status.INSUFFICIENT_STOCKS);
                    }

                }
            });

        });
        orderResponseProducer.sendOrderResponse(buildOrderResponse(order));
    }


    private OrderResponse buildOrderResponse(Order order){

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setStatus(order.getStatus());
        if (order.getStatus().equals(Status.INSUFFICIENT_STOCKS))
        {
            response.setErrorMessage("Stoc de produse insuficient");
        }
        return response;
    }
}
