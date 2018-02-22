package rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import model.Edge;
import model.Vertex;
import org.json.JSONObject;
import org.json.JSONTokener;
import services.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class ServiceDelegatingConsumer extends DefaultConsumer
{
    final Collection<Service> services = new HashSet();

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public ServiceDelegatingConsumer(final Channel channel)
    {
        super(channel);
    }

    public void addService(final Service service)
    {
        this.services.add(service);
    }

    @Override
    public void handleDelivery(final String consumerTag, final Envelope envelope,
                               final AMQP.BasicProperties properties, final byte[] body) throws IOException
    {
        final JSONObject value = new JSONObject(new JSONTokener(new InputStreamReader(
                new ByteArrayInputStream(body),
                Objects.toString(properties.getContentEncoding(),"UTF-8"))));

        if (!this.services.isEmpty())
        {
            final Edge newEdge = new Edge(
                    new Vertex(String.valueOf(value.getInt("id"))),
                    new Vertex(value.getString("device")),
                    -value.getInt("rssi")-50);

            for(Service service : this.services)
            {
                service.addEdge(newEdge);
            }
        }

        this.getChannel().basicAck(envelope.getDeliveryTag(), false);
    }
}
