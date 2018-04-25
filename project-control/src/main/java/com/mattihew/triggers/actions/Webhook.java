package com.mattihew.triggers.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Webhook implements Action, Runnable
{
    private final static Executor threadFactory = Executors.newCachedThreadPool();

    private final URL url;

    public Webhook(final URL url)
    {
        this.url = url;
    }

    @Override
    public void run()
    {
        try
        {
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(false);

            final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println(in.readLine());
            in.close();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void trigger()
    {
        threadFactory.execute(this);
    }
}
