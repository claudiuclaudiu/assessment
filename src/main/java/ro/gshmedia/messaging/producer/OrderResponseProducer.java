package ro.gshmedia.messaging.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rabbitmq.client.Channel;
import ro.gshmedia.messaging.model.OrderResponse;

import java.io.IOException;


public class OrderResponseProducer {

    private static final String ORDERS_RESPONSE_QUEUE = "ORDERS_RESPONSE";

    private Channel channel;

    public OrderResponseProducer(Channel channel) {
        this.channel = channel;
    }

    public void sendOrderResponse(OrderResponse orderResponse) throws IOException {
        channel.queueDeclare(ORDERS_RESPONSE_QUEUE, false, false, false, null);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(orderResponse);
        channel.basicPublish("", ORDERS_RESPONSE_QUEUE, null, json.getBytes());
    }

}
