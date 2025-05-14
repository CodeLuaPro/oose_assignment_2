package edu.curtin.app.town_related;

import edu.curtin.app.railways.RailwayController;

import java.util.*;

//PURPOSE: Holds the towns and railway controllers and lets the outside classes access them.
public class TownManager {
    //map for fast lookup of a town
    private Map<String, Town> towns = new HashMap<>();
    //list for iteration
    private List<Town> townsList = new ArrayList<>();
    private List<RailwayController> tracks = new ArrayList<>();


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
        //Town A may be listed as town A or town B in the railway controller object, so must check
        //both combinations
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
