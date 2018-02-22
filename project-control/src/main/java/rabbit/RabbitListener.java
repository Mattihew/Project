package rabbit;

import com.rabbitmq.client.*;
import services.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitListener
{
    private String host;
    private String username;
    private String password;
    private String exchange;

    private ServiceDelegatingConsumer consumer;

    public RabbitListener(final String host, final String username, final String password, final String exchange)
    {
        this.host = host;
        this.username = username;
        this.password = password;
        this.exchange = exchange;

    }

    public void start() throws IOException, TimeoutException
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.host);
        factory.setUsername(this.username);
        factory.setPassword(this.password);

        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(this.exchange, BuiltinExchangeType.FANOUT, false);

        final String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, this.exchange, "");

        this.consumer = new ServiceDelegatingConsumer(channel);
        channel.basicConsume(queue, false, this.consumer);
    }

    public void addService(final Service service)
    {
        this.consumer.addService(service);
    }
}
