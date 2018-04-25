package com.mattihew.triggers.zones;

import com.mattihew.model.Point;

import java.util.Collection;
import java.util.Objects;

public class MultiStationZone extends Zone
{
    private final Collection<Zone> zones;
    private final Method method;

    public static MultiStationZone usingAdd(final Collection<Zone> zones)
    {
        return new MultiStationZone(zones, Method.AND);
    }

    public static MultiStationZone usingOr(final Collection<Zone> zones)
    {
        return new MultiStationZone(zones, Method.OR);
    }

    public MultiStationZone(final Collection<Zone> zones, final Method method)
    {
        this.zones = zones;
        this.method = method;
    }

    @Override
    public State inRange(final Point point)
    {
        State state = State.NULL;
        for (final Zone zone : zones)
        {
            state = this.method.perform(state, zone.inRange(point));
        }
        return state;
    }

    @Override
    public int hashCode()
    {
        return 21 * Objects.hash(zones, method);
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == this) return true;
        if (!(obj instanceof MultiStationZone)) return false;
        final Collection<Zone> otherZones = ((MultiStationZone) obj).zones;
        return method.equals(((MultiStationZone) obj).method) && zones.size() == otherZones.size() && zones.containsAll(otherZones);
    }

    public enum Method
    {
        AND
        {
            @Override
            public State perform(final State op1, final State op2)
            {
                return op1.and(op2);
            }
        },
        OR
        {
            @Override
            public State perform(final State op1, final State op2)
            {
                return op1.or(op2);
            }
        };

        public abstract State perform(final State op1, final State op2);
    }
}
