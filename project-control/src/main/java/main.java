import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class main
{
    public static void main (final String... args) throws IOException, TimeoutException
    {
        new RabbitListener();
    }
}
