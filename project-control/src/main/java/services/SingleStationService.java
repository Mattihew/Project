package services;

import actions.Action;
import actions.Webhook;
import model.Point;
import model.SingleStationZone;
import model.Vertex;
import model.Zone;
import model.edge.Edge;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SingleStationService implements Service
{
    private final Vertex station;



    final Map<SingleStationZone, Action> triggers = new HashMap<>();

    private State state = new State(2);
    private boolean oldState = false;

    public SingleStationService(final Vertex stationId)
    {
        this.station = stationId;
        try
        {
            final Webhook webhook = new Webhook(new URL("https://maker.ifttt.com/trigger/lights_on/with/key/c_6XVf_JZVQKfX_4_et0TC"));
            final Webhook webhook2 = new Webhook(new URL("https://maker.ifttt.com/trigger/lights_off/with/key/c_6XVf_JZVQKfX_4_et0TC"));
            this.addTrigger(new SingleStationZone(this.station,0, 40), webhook);
            this.addTrigger(new SingleStationZone(this.station,45), webhook2);
        }
        catch (final MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addPeripheralEdge(final Edge edge)
    {
        if (edge.getStation().equals(this.station))
        {
            checkTriggers(new Point(edge));
        }
        else
        {
            //todo wrong station
        }
    }

    public void addTrigger(final SingleStationZone zone, final Action action)
    {
        this.triggers.put(zone, action);
    }

    private void checkTriggers(final Point point)
    {
        for (final Map.Entry<SingleStationZone, Action> entry : this.triggers.entrySet())
        {
            if (entry.getKey().inRange(point).equals(Zone.State.IN))
            {
                entry.getValue().trigger();
            }
        }
    }
}
