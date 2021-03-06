package com.mattihew.model;

import com.mattihew.model.edge.Edge;

import java.util.*;

public class Point
{
    private final Collection<Edge> edges;
    private Vertex peripheral;
    private long time = System.currentTimeMillis();

    public Point (final Vertex peripheral)
    {
        this.edges = Collections.emptySet();
        this.peripheral = peripheral;
    }

    public Point (final Edge edge)
    {
        this.edges = Collections.singleton(edge);
        this.peripheral = edge.getPeripheral();
        this.time = edge.getTime();
    }

    public Point(final Collection<Edge> edges)
    {
        for (final Edge edge : edges)
        {
            if (this.peripheral == null)
            {
                this.peripheral = edge.getPeripheral();
            }
            else if (!this.peripheral.equals(edge.getPeripheral()))
            {
                throw new IllegalArgumentException("all edges have to be for the same peripheral");
            }
            if (this.time > edge.getTime())
            {
                this.time = edge.getTime();
            }
        }
        this.edges = Collections.unmodifiableCollection(new ArrayList<>(edges));
    }

    public Vertex getPeripheral()
    {
        return this.peripheral;
    }

    public Collection<Edge> getEdges()
    {
        return this.edges;
    }

    public Edge getEdge(final Vertex station)
    {
        for (final Edge edge : this.edges)
        {
            if (edge.getStation().equals(station))
            {
                return edge;
            }
        }
        return null;
    }

    public long getTime()
    {
        return this.time;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Point)) return false;

        final Point other = (Point) obj;
        return this.peripheral.equals(other.peripheral) &&
               this.edges.size() == other.edges.size() && this.edges.containsAll(other.edges);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(edges, peripheral);
    }
}
