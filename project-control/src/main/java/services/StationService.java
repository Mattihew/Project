package services;

import actions.Action;
import actions.Webhook;
import model.*;
import model.edge.Edge;
import model.zones.SingleStationZone;
import model.zones.Zone;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class StationService implements Service
{
    private final Vertex station;

    final Map<Zone, Action> triggers = new HashMap<>();

    private State state = new State(2);
    private boolean oldState = false;

    public StationService(final Vertex stationId)
    {
        this.station = stationId;
        try
        {
            final Webhook webhook = new Webhook(new URL("https://maker.ifttt.com/trigger/lights_on/with/key/c_6XVf_JZVQKfX_4_et0TC"));
            final Webhook webhook2 = new Webhook(new URL("https://maker.ifttt.com/trigger/lights_off/with/key/c_6XVf_JZVQKfX_4_et0TC"));
            this.addTrigger(new SingleStationZone(this.station, new Vertex("JinouBeacon"),0, 20), webhook);
            this.addTrigger(new SingleStationZone(this.station, new Vertex("JinouBeacon"),25), webhook2);
        }
        catch (final MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
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

    private void checkTriggers(final Point newPoint, final Point prevPoint)
    {
        for (final Map.Entry<Zone, Action> entry : this.triggers.entrySet())
        {
            final Zone zone = entry.getKey();
            if (zone.inRange(newPoint).equals(Zone.State.IN) && !zone.inRange(prevPoint).equals(Zone.State.IN))
            {
                entry.getValue().trigger();
            }
        }
    }
}
