import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import model.Vertex;
import rabbit.RabbitListener;
import services.SingleStationService;

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

        final RabbitListener listener =
                new RabbitListener("rx");
        listener.start(connection);
        listener.addService(new SingleStationService(new Vertex("1")));
    }
}
