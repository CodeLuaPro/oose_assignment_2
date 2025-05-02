package edu.curtin.app.town_related;

import edu.curtin.app.railways.RailwayController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TownManager {
    Map<String, Town> towns = new HashMap<String, Town>();
    List<RailwayController> tracks = new ArrayList<>();


    public TownManager() {

    }

    public Town getTown(String townName) {
        return towns.get(townName);
    }

    public void addTown(Town town, String name) {
        towns.put(name, town);

    }

    public void addTrack(RailwayController track) {
        tracks.add(track);
    }

    public RailwayController getTrackByTowns(Town townA, Town townB) {
        RailwayController retTrack = null;
        for (RailwayController track : tracks) {
            if ((track.getTownA().equals(townA) && track.getTownB().equals(townB))
                    || (track.getTownA().equals(townB) && track.getTownB().equals(townA))) {
                retTrack = track;
            }
        }
        return retTrack;
    }
}
