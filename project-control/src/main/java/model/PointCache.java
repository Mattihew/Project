package model;

import model.edge.Edge;

import java.util.*;

public class PointCache
{
    private static long MAX_AGE = Long.MAX_VALUE;
    private static final Map<Vertex, Point> points = new HashMap<>();


    public static Point getPoint(final Vertex peripheral)
    {
        return PointCache.points.get(peripheral);
    }

    public static Point toPoint (final Edge edge)
    {
        final Point oldPoint = PointCache.getPoint(edge.getPeripheral());
        final Point newPoint;
        if (oldPoint == null)
        {
            newPoint = new Point(edge);
        }
        else
        {
            final List<Edge> edges = new ArrayList<>(oldPoint.getEdges());
            for (final ListIterator<Edge> i = edges.listIterator(); i.hasNext();)
            {
                final Edge current = i.next();
                if (current.getStation().equals(edge.getStation()))
                {
                    i.set(edge);
                }
                else if (System.currentTimeMillis() - current.getTime() > MAX_AGE)
                {
                    i.remove();
                }
            }
            newPoint = new Point(edges);
        }
        PointCache.points.put(newPoint.getPeripheral(), newPoint);
        return newPoint;
    }
}
