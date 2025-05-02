package edu.curtin.app.town_related;

import edu.curtin.app.railways.RailwayController;

import java.util.*;

public class TownManager {
    Map<String, Town> towns = new HashMap<String, Town>();
    List<Town> townsList = new ArrayList<>();
    List<RailwayController> tracks = new ArrayList<>();


    public TownManager() {

    }

    public Town getTown(String townName) {
        return towns.get(townName);
    }

    public void addTown(Town town, String name) {
        towns.put(name, town);
        townsList.add(town);
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

    public List<Town> getTownsAsList() {
        return Collections.unmodifiableList(townsList);
    }

    public List<RailwayController> getTracksAsList() {
        return Collections.unmodifiableList(tracks);
    }
}
