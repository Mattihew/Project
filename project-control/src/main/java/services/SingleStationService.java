package services;

import actions.Action;
import actions.Webhook;
import model.Edge;

public class SingleStationService implements Service
{
    private String stationId;

    private Webhook webhook = new Webhook("c_6XVf_JZVQKfX_4_et0TC");

    private State state = new State(2);
    private boolean oldState = false;

    public SingleStationService()
    {
        super();
    }

    public SingleStationService(final String stationId)
    {
        this.stationId = stationId;
    }

    @Override
    public void addEdge(final Edge edge)
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
