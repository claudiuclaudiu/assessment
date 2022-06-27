package ro.gshmedia.messaging.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ClientFactory {

    Connection connection;
    Channel channel;
    ConnectionFactory factory;

    public ClientFactory() throws IOException, TimeoutException {
        createClientChannel();
    }

    public void closeConnection() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ConnectionFactory getFactory() {
        return factory;
    }

    public void setFactory(ConnectionFactory factory) {
        this.factory = factory;
    }

    private void createClientChannel() throws IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        factory.setPort(15678);
        factory.setUsername("user1");
        factory.setPassword("MyPassword");
    }
}
