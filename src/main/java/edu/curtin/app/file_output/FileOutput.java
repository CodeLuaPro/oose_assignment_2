package edu.curtin.app.file_output;

import edu.curtin.app.interfaces.NewDayObserver;
import edu.curtin.app.railways.RailwayController;
import edu.curtin.app.town_related.Town;
import edu.curtin.app.town_related.TownManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileOutput implements NewDayObserver {
    private static final Logger logger = Logger.getLogger(FileOutput.class.getName());
    private TownManager townManager;
    private String fileName;

    public FileOutput(TownManager townManager, String fileName) {
        this.townManager = townManager;
        this.fileName = fileName;
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

    public void writeToFile() { //NOPMD
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(buildString());
        }
        catch (IOException e) {
            logger.log(Level.WARNING, "Error writing to file");
        }
    }

    @Override
    public void update() {
        writeToFile();
    }
}
