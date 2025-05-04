package edu.curtin.app.file_output;

import edu.curtin.app.railways.RailwayController;
import edu.curtin.app.town_related.Town;
import edu.curtin.app.town_related.TownManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileOutput {
    private TownManager townManager;


    public FileOutput(TownManager townManager) {
        this.townManager = townManager;
    }

    public String buildString() {
        String retStr = "graph Towns {\n";
        List<Town> towns = townManager.getTownsAsList();
        List<RailwayController> tracks = townManager.getTracksAsList();

        for (Town town : towns) {
            retStr += town.getName() + "\n";
        }
        retStr += "\n";

        for (RailwayController track : tracks) {
            String townAName = track.getTownA().getName();
            String townBName = track.getTownB().getName();
            retStr += townAName + " -- " + townBName + " " + track.getFileOptions() + "\n";
        }

        retStr += "}";

        return retStr;
    }

    public void writeToFile(String filename) throws IOException { //NOPMD
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(buildString());
        }
    }

}
