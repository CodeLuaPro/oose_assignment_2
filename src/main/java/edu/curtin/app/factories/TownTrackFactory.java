package edu.curtin.app.factories;

import edu.curtin.app.railways.RailwayController;
import edu.curtin.app.town_related.Town;

//PURPOSE: to initialise towns and railway controllers
public class TownTrackFactory {
    public TownTrackFactory() {

    }

    public Town createTown(String townName, int population) {
        if (population < 0) {
            throw new IllegalArgumentException("Population must be non-negative");
        }
        return new Town(population, townName);
    }

    public RailwayController createRailwayController(Town townA, Town townB) {
        if (townA == null || townB == null) {
            throw new IllegalArgumentException("Town and Railway controller cannot be null");
        }
        return new RailwayController(townA, townB);
    }
}
