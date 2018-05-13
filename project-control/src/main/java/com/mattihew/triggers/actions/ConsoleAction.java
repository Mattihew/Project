package com.mattihew.triggers.actions;

import org.json.JSONObject;

import java.io.PrintStream;

public class ConsoleAction implements Action
{
    private final PrintStream stream;
    private final String msg;

    public ConsoleAction(final String msg)
    {
        this(msg, System.out);
    }

    public ConsoleAction(final String msg, final PrintStream stream)
    {
        this.stream = stream;
        this.msg = msg;
    }

    @Override
    public void trigger()
    {
        this.stream.println(this.msg);
    }

    public static class ConsoleActionFactory extends ActionFactory.AbstractActionFactory<ConsoleAction>
    {
        ConsoleActionFactory()
        {
            super();
        }

        @Override
        public ConsoleAction fromJson(final JSONObject json)
        {
            final String msg = json.getString("msg");
            return new ConsoleAction(msg);
        }
    }
}
