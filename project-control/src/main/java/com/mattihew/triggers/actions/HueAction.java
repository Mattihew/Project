package com.mattihew.triggers.actions;

import com.mattihew.Props;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HueAction implements Action
{
    private static String ON_KEY = "on";
    private static String BRI_KEY = "bri";
    private static String HUE_KEY = "hue";
    private static String SAT_KEY = "sat";

    private URL url;
    private byte[] body;

    public HueAction(final int light, final boolean turnOn)
    {
        this(light, turnOn, null, null, null);
    }

    public HueAction(final int light, final Boolean turnOn, final Byte brightness, final Short hue, final Byte saturation)
    {
        try
        {
            this.url = new URL(Props.HueURL.format(light));
            final JSONObject jsonBody = new JSONObject();
            jsonBody.put(ON_KEY, turnOn);
            jsonBody.put(BRI_KEY, brightness);
            jsonBody.put(HUE_KEY, hue);
            jsonBody.put(SAT_KEY, saturation);
            this.body = jsonBody.toString().getBytes(StandardCharsets.UTF_8);
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
            connection.setRequestMethod(Props.HueMethod.getValue());
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

    public static class HueActionFactory extends ActionFactory.AbstractActionFactory<HueAction>
    {
        HueActionFactory()
        {
            super();
        }

        @Override
        public HueAction fromJson(final JSONObject json)
        {
            final int light = json.getInt(Props.HueActionFactory_Light.getValue());
            final Object onObj = json.opt(ON_KEY);
            final Object briObj = json.opt(BRI_KEY);
            final Object hueObj = json.opt(HUE_KEY);
            final Object satObj = json.opt(SAT_KEY);

            final Boolean on = onObj instanceof Boolean ? (Boolean) onObj : null;
            final Byte bri = briObj instanceof Number ? ((Number) briObj).byteValue() : null;
            final Short hue = hueObj instanceof Number ? ((Number) hueObj).shortValue() : null;
            final Byte sat = satObj instanceof Number ? ((Number) satObj).byteValue() : null;
            return new HueAction(light, on, bri, hue, sat);
        }
    }
}
