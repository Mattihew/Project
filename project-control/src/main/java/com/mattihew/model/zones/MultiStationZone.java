package com.mattihew.model.zones;

import com.mattihew.model.Point;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class MultiStationZone extends Zone
{
    private final Collection<Zone> singleStationZones;

    public MultiStationZone(final Zone... zones)
    {
        this(Arrays.asList(zones));
    }

    public MultiStationZone(final Collection<Zone> singleStationZones)
    {
        this.singleStationZones = singleStationZones;
    }

    @Override
    public State inRange(final Point point)
    {
        State state = State.NULL;
        for (final Zone zone : singleStationZones)
        {
            state = state.and(zone.inRange(point));
        }
        return state;
    }

    @Override
    public int hashCode()
    {
        return 21 * Objects.hash(singleStationZones);
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == this) return true;
        if (!(obj instanceof MultiStationZone)) return false;
        final Collection<Zone> otherZones = ((MultiStationZone) obj).singleStationZones;
        return singleStationZones.size() == otherZones.size() && singleStationZones.containsAll(otherZones);
    }
}
