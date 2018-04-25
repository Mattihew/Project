package com.mattihew.actions;

import com.mattihew.Props;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory
{
    private static final Map<String, AbstractActionFactory<? extends Action>> factories = new HashMap<>();

    static
    {
        factories.put(Props.HueActionFactory_Type.getValue(), new HueAction.HueActionFactory());
    }

    public static Action fromJson(final String json)
    {
        return ActionFactory.fromJson(new JSONObject(json));
    }

    public static Action fromJson(final JSONObject json)
    {
        final String type = json.getString(Props.ActionFactory_Type.getValue());
        final AbstractActionFactory<? extends Action> factory = factories.get(type);
        if (factory == null)
        {
            return null;
        }
        else
        {
            return factory.fromJson(json);
        }
    }

    static abstract class AbstractActionFactory<A extends Action>
    {
        public abstract A fromJson(final JSONObject json);
    }
}
