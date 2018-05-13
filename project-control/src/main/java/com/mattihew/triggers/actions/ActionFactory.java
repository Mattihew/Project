package com.mattihew.triggers.actions;

import com.mattihew.Props;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ActionFactory
{
    private static final Map<String, AbstractActionFactory<? extends Action>> factories = new HashMap<>();

    static
    {
        factories.put(Props.HueActionFactory_Type.getValue(), new HueAction.HueActionFactory());
        factories.put("console", new ConsoleAction.ConsoleActionFactory());
    }

    public static Action fromJson(final Object json)
    {
        if (json instanceof JSONObject)
        {
            return ActionFactory.fromJson((JSONObject) json);
        }
        else if (json instanceof JSONArray)
        {
            return ActionFactory.fromJson((JSONArray) json);
        }
        else if (json instanceof String)
        {
            return ActionFactory.fromJson((String) json);
        }
        else
        {
            throw new JSONException("invalid json input");
        }
    }

    public static Action fromJson(final String json)
    {
        final JSONTokener tokener = new JSONTokener(json);
        try
        {
            return ActionFactory.fromJson(new JSONObject(tokener));
        }
        catch (final JSONException e)
        {
            return ActionFactory.fromJson(new JSONArray(tokener));
        }
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

    public static Action fromJson(final JSONArray json)
    {
        final Collection<Action> actions = new ArrayList<>(json.length());
        for (final Object jsonAction : json)
        {
            actions.add(ActionFactory.fromJson(jsonAction));
        }
        return new ActionCollection(actions);
    }

    static abstract class AbstractActionFactory<A extends Action>
    {
        public abstract A fromJson(final JSONObject json);
    }
}
