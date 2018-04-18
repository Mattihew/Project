package com.mattihew.actions;

import java.util.Collection;

public class ActionCollection implements Action
{
    private final Collection<Action> actions;

    public ActionCollection(final Collection<Action> actions)
    {
        this.actions = actions;
    }

    @Override
    public void trigger()
    {
        for (final Action action : this.actions)
        {
            action.trigger();
        }
    }
}
