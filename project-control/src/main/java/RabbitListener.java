import com.rabbitmq.client.*;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

public class RabbitListener
{
    public RabbitListener() throws IOException, TimeoutException
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.185");
        factory.setUsername("demo");
        factory.setPassword("demo");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("rx", "fanout", false);

        final String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, "rx", "");

        Consumer consumer = new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(final String consumerTag, final Envelope envelope,
                                       final AMQP.BasicProperties properties, final byte[] body) throws IOException
            {
                final JSONObject value = new JSONObject(new JSONTokener(
                        new InputStreamReader(new ByteArrayInputStream(body), "UTF-8")));
                System.out.println(value);

            }
        };
        channel.basicConsume(queue, true, consumer);
    }
}
