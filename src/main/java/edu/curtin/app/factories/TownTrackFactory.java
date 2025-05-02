package edu.curtin.app.factories;

import edu.curtin.app.railways.RailwayController;
import edu.curtin.app.town_related.Town;

public class TownTrackFactory {
    public TownTrackFactory() {

    }

    public Town createTown(String townName, int population) {
        return new Town(population, townName);
    }

    public RailwayController createRailwayController(Town townA, Town townB) {
        return new RailwayController(townA, townB);
    }
}
