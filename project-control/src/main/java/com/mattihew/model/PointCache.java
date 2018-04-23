package com.mattihew.model;

import com.mattihew.Props;
import com.mattihew.model.edge.Edge;
import com.mattihew.services.Service;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PointCache
{
    private static final long MAX_AGE = Long.valueOf(Props.PointCacheMaxAge.getValue());
    private static final Map<Vertex, Point> points = Collections.synchronizedMap(new HashMap<Vertex, Point>());

    private static final Collection<Service> services = new ArrayList<>();
    private static final ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();

    static
    {
        ex.scheduleAtFixedRate(new CullEdges(), MAX_AGE, MAX_AGE, TimeUnit.MILLISECONDS);
    }

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

    public static void addService(final Service service)
    {
        services.add(service);
    }

    private static class CullEdges implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                synchronized (points)
                {
                    for (final Iterator<Map.Entry<Vertex, Point>> j = points.entrySet().iterator(); j.hasNext();)
                    {
                        final Map.Entry<Vertex, Point> entry = j.next();
                        final Point point = entry.getValue();
                        if (System.currentTimeMillis() - point.getTime() > MAX_AGE)
                        {
                            final Collection<Edge> edges = new ArrayList<>(point.getEdges());
                            for (final Iterator<Edge> i = edges.iterator(); i.hasNext();)
                            {
                                if (System.currentTimeMillis() - i.next().getTime() > MAX_AGE)
                                {
                                    i.remove();
                                }
                            }
                            if (!edges.isEmpty())
                            {
                                final Point updatedPoint = new Point(edges);
                                for (final Service service : services)
                                {
                                    service.addPeripheralPoint(updatedPoint);
                                }
                                entry.setValue(updatedPoint);
                            }
                            else
                            {
                                final Point emptyPoint = new Point(point.getPeripheral());
                                for (final Service service : services)
                                {
                                    service.addPeripheralPoint(emptyPoint);
                                }
                                j.remove();
                            }
                        }
                    }
                }
            }
            catch (final Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
