package com.mattihew.triggers.actions;

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
        this.stream.print(this.msg);
    }
}
