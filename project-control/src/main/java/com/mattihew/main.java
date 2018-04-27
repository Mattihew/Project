package com.mattihew;

import com.mattihew.model.PointCache;
import com.mattihew.rabbit.RabbitListener;
import com.mattihew.services.Service;
import com.mattihew.services.TriggerFileReader;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class main
{
    public static void main (final String... args) throws IOException, TimeoutException
    {
        System.out.println("Starting");
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.100");
        factory.setUsername("demo");
        factory.setPassword("demo");
        Connection connection = factory.newConnection();

        final Service service = new Service();
        final RabbitListener listener = new RabbitListener("rx");
        listener.start(connection);
        listener.addService(service);
        PointCache.addService(service);

        final TriggerFileReader fileReader = new TriggerFileReader(service);
        fileReader.readFile(ClassLoader.getSystemResourceAsStream("triggers.json")); //NON-NLS
        System.out.println("Started");
    }
}
