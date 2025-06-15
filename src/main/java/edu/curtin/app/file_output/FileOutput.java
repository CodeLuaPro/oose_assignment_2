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

//PURPOSE: output the progress of the simulation to a file
public class FileOutput implements NewDayObserver {
    private static final Logger logger = Logger.getLogger(FileOutput.class.getName());
    private TownManager townManager;
    private String fileName;

    public FileOutput(TownManager townManager, String fileName) {
        this.townManager = townManager;
        this.fileName = fileName;
    }

    //PURPOSE: build the string that will eventually be written to a file
    private String buildString() {
        String retStr = "graph Towns {\n";
        List<Town> towns = townManager.getTownsAsList();
        List<RailwayController> tracks = townManager.getTracksAsList();

        //append the name of each town to the string
        for (Town town : towns) {
            retStr += town.getName() + "\n";
            logger.log(Level.INFO, "reading town info for file output string");
        }
        retStr += "\n";

        //append all existing railway controllers (ready or building) to the file
        for (RailwayController track : tracks) {
            logger.log(Level.INFO, "reading track info for file output string");
            String townAName = track.getTownA().getName();
            String townBName = track.getTownB().getName();
            retStr += townAName + " -- " + townBName + " " + track.getFileOptions() + "\n";
        }

        retStr += "}";

        return retStr;
    }

    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            logger.log(Level.INFO, "writing to file");
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
