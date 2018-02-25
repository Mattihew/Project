package model.edge;

import model.Vertex;

public class PeripheralEdge implements Edge
{
    private final Vertex peripheral;
    private final Vertex station;
    private final int distance;
    private final long time;

    public PeripheralEdge(final Vertex peripheral, final Vertex station, final int distance)
    {
        this(peripheral, station, distance, System.currentTimeMillis());
    }

    public PeripheralEdge (final Vertex peripheral, final Vertex station, final int distance, final long time)
    {
        this.peripheral = peripheral;
        this.station = station;
        this.distance = distance;
        this.time = time;
    }

    public Vertex getPeripheral()
    {
        return this.peripheral;
    }

    public Vertex getStation()
    {
        return this.station;
    }

    @Override
    public int getDistance()
    {
        return this.distance;
    }

    public long getTime()
    {
        return this.time;
    }

    @Override
    public boolean contains(final Vertex vertex)
    {
        return this.peripheral.equals(vertex) || this.station.equals(vertex);
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == this) return true;
        if (!(obj instanceof PeripheralEdge)) return false;
        final PeripheralEdge other = (PeripheralEdge) obj;
        return other.contains(this.peripheral) && other.contains(this.station)
                && other.distance == this.distance && other.time == this.time;
    }
}
