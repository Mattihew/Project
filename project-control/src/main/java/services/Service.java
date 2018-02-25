package services;

import model.edge.PeripheralEdge;
import model.edge.StationEdge;

public interface Service
{
    public void addPeripheralEdge(final PeripheralEdge edge);

    public void addStationEdge(final StationEdge edge);
}
