package services;

import actions.Webhook;
import model.Vertex;
import model.edge.Edge;
import model.edge.PeripheralEdge;
import model.edge.StationEdge;

public class SingleStationService implements Service
{
    private final Vertex station;

    private Webhook webhook = new Webhook("c_6XVf_JZVQKfX_4_et0TC");

    private State state = new State(2);
    private boolean oldState = false;

    public SingleStationService(final Vertex stationId)
    {
        this.station = stationId;
    }

    @Override
    public void addPeripheralEdge(final PeripheralEdge edge)
    {
        if (edge.getStation().equals(this.station))
        {
            if (edge.getDistance() > 45)
            {
                this.state.setValue(false);
            }
            else if (edge.getDistance() > 40)
            {
                this.state.decrement();
            }
            else
            {
                this.state.increment();
            }

            if (state.getValue() && !oldState)
            {
                webhook.trigger("lights_on");
            }
            else if (!state.getValue() && oldState)
            {
                webhook.trigger("lights_off");
            }
            this.oldState = this.state.getValue();
            System.out.println(edge);
        }
    }

    @Override
    public void addStationEdge(final StationEdge edge)
    {
        //NOOP
    }
}
