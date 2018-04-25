package com.mattihew.services;

import com.mattihew.triggers.actions.Action;
import com.mattihew.model.*;
import com.mattihew.model.edge.Edge;
import com.mattihew.triggers.zones.Zone;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static com.mattihew.triggers.zones.Zone.State.IN;

public class Service
{
    private final Map<Action, Zone> triggers = new HashMap<>();

    private State state = new State(2);
    private boolean oldState = false;

    public void addPeripheralEdge(final Edge edge)
    {
        if(edge.getPeripheral().toString().equals("JinouBeacon"))
        //if(edge.getPeripheral().toString().equals("XT1580"))
        System.out.println(edge.getDistance()); //todo remove
        final Point prevPoint = PointCache.getPoint(edge.getPeripheral());
        checkTriggers(PointCache.toPoint(edge), prevPoint);
    }

    public void addPeripheralPoint(final Point point)
    {
        final Point prevPoint = PointCache.getPoint(point.getPeripheral());
        checkTriggers(point, prevPoint);
    }

    public void addTrigger(final Zone zone, final Action action)
    {
        this.triggers.put(action, zone);
    }

    public Map<Action, Zone> getTriggers()
    {
        return Collections.unmodifiableMap(this.triggers);
    }

    private void checkTriggers(final Point newPoint, final Point prevPoint)
    {
        for (final Map.Entry<Action, Zone> entry : this.triggers.entrySet())
        {
            final Zone zone = entry.getValue();
            if (zone.inRange(newPoint).equals(IN) &&
               !zone.inRange(prevPoint).equals(IN))
            {
                entry.getKey().trigger();
            }
        }
    }
}
