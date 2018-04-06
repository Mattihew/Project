package rabbit;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RpcServer
{
    private static final String EXCHANGE = "testQ";

    public static void main(final String... args) throws IOException, TimeoutException
    {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.0.100");
        factory.setUsername("demo");
        factory.setPassword("demo");
        Connection connection = factory.newConnection();

        RpcServer rpc = new RpcServer();
        rpc.start(connection);
    }

    public void start(final Connection connection) throws IOException, TimeoutException
    {
        final Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT, false, true, null);

        final String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, EXCHANGE, "");

        channel.basicConsume(queue, false, new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body) throws IOException
            {
                final AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();
                this.getChannel().basicPublish(
                        "",
                        properties.getReplyTo(),
                        replyProps,
                        "hello world".getBytes(StandardCharsets.UTF_8));
                this.getChannel().basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}


