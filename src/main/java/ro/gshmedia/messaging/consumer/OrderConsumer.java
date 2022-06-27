package ro.gshmedia.messaging.consumer;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import ro.gshmedia.messaging.model.OrderRequest;

import java.io.IOException;

public class OrderConsumer extends DefaultConsumer {

    private static final String ORDERS_QUEUE = "ORDERS";

    Channel channel;

    public OrderConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }


    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
    }

    public OrderRequest getOrderFromQueue() throws IOException {
        channel.queueDeclare(ORDERS_QUEUE, false, false, false, null);
        String json = channel.basicConsume(ORDERS_QUEUE, this);
        // mesajele vin in format json asa trebuie traduse in clase java ca se le putem folosi
        // cauta ce face libraria asta ca sa intelegi mai bine
        Gson gson = new Gson();
        OrderRequest request = gson.fromJson(json, OrderRequest.class);
        return request;
    }
}
