package com.mattihew.rabbit;

import com.rabbitmq.client.*;
import com.mattihew.services.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitListener
{
    private String exchange;

    private ServiceDelegatingConsumer consumer;

    public RabbitListener(final String exchange)
    {
        this.exchange = exchange;
    }

    public void start(final Connection connection) throws IOException, TimeoutException
    {
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
