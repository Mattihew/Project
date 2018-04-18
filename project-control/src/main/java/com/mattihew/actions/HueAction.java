package com.mattihew.actions;

import com.mattihew.Props;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import static com.mattihew.Props.Key.*;

public class HueAction implements Action
{
    private URL url;
    private byte[] body;

    public HueAction(final int light, final boolean turnOn)
    {
        try
        {
            this.url = new URL(Props.format(HueURL, light));
            this.body = Props.format(HueBody, turnOn).getBytes(StandardCharsets.UTF_8);
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
            connection.setRequestMethod("PUT"); //NON-NLS
            //connection.setRequestProperty("Content-Length", Integer.toString(body.length));
            connection.setFixedLengthStreamingMode(body.length);
            connection.setDoOutput(true);

            final OutputStream out = connection.getOutputStream();
            out.write(body);
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