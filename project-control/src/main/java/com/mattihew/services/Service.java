package com.mattihew.services;

import com.mattihew.actions.Action;
import com.mattihew.model.*;
import com.mattihew.model.edge.Edge;
import com.mattihew.model.zones.Zone;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static com.mattihew.model.zones.Zone.State.*;

public class Service
{
    private final Map<Zone, Action> triggers = new HashMap<>();

    private State state = new State(2);
    private boolean oldState = false;

    public void addPeripheralEdge(final Edge edge)
    {
        if(edge.getPeripheral().toString().equals("JinouBeacon"))
        System.out.println(edge.getDistance()); //todo remove
        final Point prevPoint = PointCache.getPoint(edge.getPeripheral());
        checkTriggers(PointCache.toPoint(edge), prevPoint);
    }

    public void addTrigger(final Zone zone, final Action action)
    {
        this.triggers.put(zone, action);
    }

    public Map<Zone, Action> getTriggers()
    {
        return Collections.unmodifiableMap(this.triggers);
    }

    private void checkTriggers(final Point newPoint, final Point prevPoint)
    {
        for (final Map.Entry<Zone, Action> entry : this.triggers.entrySet())
        {
            final Zone zone = entry.getKey();
            if (zone.inRange(newPoint).equals(IN) &&
               !zone.inRange(prevPoint).equals(IN))
            {
                entry.getValue().trigger();
            }
        }
    }
}
