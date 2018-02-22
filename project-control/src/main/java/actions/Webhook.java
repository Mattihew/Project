package actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Webhook
{
    private final String key;
    private final Executor threadFactory = Executors.newSingleThreadExecutor();

    public Webhook(final String key)
    {
        this.key = key;
    }

    public void trigger(final String event)
    {
        this.trigger(event, null);
    }

    public void trigger(final String event, final String value)
    {
        threadFactory.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String urlString = "https://maker.ifttt.com/trigger/" + event + "/with/key/" + Webhook.this.key;
                    if (value != null)
                    {
                        urlString += "?value1=" + value;
                    }
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(false);

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    System.out.println(in.readLine());
                }
                catch (final IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
