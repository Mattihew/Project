package model;

import model.edge.Edge;

import java.util.*;

public class Point
{
    private final Collection<Edge> edges;
    private Vertex peripheral;

    public Point (final Edge edge)
    {
        this.edges = Collections.singleton(edge);
        this.peripheral = edge.getPeripheral();
    }

    public Point(final Edge... edges)
    {
        this(Arrays.asList(edges));
    }

    public Point(final Collection<Edge> edges)
    {
        this.edges = Collections.unmodifiableCollection(new ArrayList<>(edges));
        for (final Edge edge : this.edges)
        {
            if (this.peripheral == null)
            {
                this.peripheral = edge.getPeripheral();
            }
            else if (!this.peripheral.equals(edge.getPeripheral()))
            {
                throw new IllegalArgumentException("all edges have to be for the same peripheral");
            }
        }

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

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Point)) return false;

        final Point other = (Point) obj;
        return this.edges.size() == other.edges.size() && this.edges.containsAll(other.edges);
    }
}
