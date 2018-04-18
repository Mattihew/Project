package com.mattihew;

import com.mattihew.actions.Action;
import com.mattihew.actions.HueAction;
import com.mattihew.model.PointCache;
import com.mattihew.model.zones.NullIncludedZone;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.mattihew.model.Vertex;
import com.mattihew.model.zones.SingleStationZone;
import com.mattihew.model.zones.Zone;
import com.mattihew.rabbit.RabbitListener;
import com.mattihew.services.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class main
{
    public static void main (final String... args) throws IOException, TimeoutException
    {
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


        final Vertex station = new Vertex("00000000acb63522");
        final Vertex peripheral = new Vertex("JinouBeacon");
        //final Vertex peripheral = new Vertex("XT1580");

        final Zone zone = new SingleStationZone(station, peripheral,0, 30);
        final Action webhook = new HueAction(3, true);

        final Zone zone2 = new NullIncludedZone(new SingleStationZone(station, peripheral,45));
        final Action webhook2 = new HueAction(3, false);

        service.addTrigger(zone, webhook);
        service.addTrigger(zone2, webhook2);
    }
}
