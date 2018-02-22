import rabbit.RabbitListener;
import services.SingleStationService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class main
{
    public static void main (final String... args) throws IOException, TimeoutException
    {
        final RabbitListener listener =
                new RabbitListener("192.168.0.185", "demo","demo", "rx");
        listener.start();
        listener.addService(new SingleStationService());
    }
}
