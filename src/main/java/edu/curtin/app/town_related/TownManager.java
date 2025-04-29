package edu.curtin.app.town_related;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TownManager {
    Map<String, Town> towns = new HashMap<String, Town>();
    List<Railway> singleTracks = new ArrayList<>();
    List<Railway> doubleTracks = new ArrayList<>();

    public TownManager() {

    }
}
