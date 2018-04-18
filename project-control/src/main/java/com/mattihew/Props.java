package com.mattihew;

import java.io.IOException;
import java.util.Properties;

public class Props
{
    private static final Properties propsFile = new Properties();

    static
    {
        try
        {
            propsFile.load(ClassLoader.getSystemResourceAsStream("config.properties")); //NON-NLS
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    private Props() throws AssertionError
    {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static String getProp(final Key key)
    {
        return propsFile.getProperty(key.toString());
    }

    public static String getProp(final Key key, final String defaultValue)
    {
        return propsFile.getProperty(key.toString(), defaultValue);
    }

    public static String format(final Key key, final Object... values)
    {
        return String.format(Props.getProp(key), values);
    }

    public enum Key
    {
        HueURL("hue.url"),
        HueBody("hue.body");

        private final String value;
        Key(final String s)
        {
            this.value = s;
        }

        @Override
        public String toString()
        {
            return this.value;
        }
    }
}
