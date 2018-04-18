package com.mattihew.actions;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.mattihew.Props;

public class HueAction implements Action
{
    private URL url;
    private String body;

    public HueAction(final int light, final boolean turnOn)
    {
        try
        {
            this.url = new URL(Props.format(Props.Key.HueURL, light));
            this.body = Props.format(Props.Key.HueBody, turnOn);
        }
        catch (final MalformedURLException e)
        {
            //todo
            e.printStackTrace();
        }
    }

    @Override
    public void trigger()
    {
        try
        {
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            //connection.setRequestProperty("Content-Length", Integer.toString(body.length()));
            connection.setFixedLengthStreamingMode(body.length());
            connection.setDoOutput(true);

            final OutputStream out = connection.getOutputStream();
            out.write(body.getBytes(StandardCharsets.UTF_8));
            out.flush();
            out.close();

            final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println(in.readLine());
            in.close();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
}
