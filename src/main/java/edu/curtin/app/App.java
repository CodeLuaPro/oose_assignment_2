package edu.curtin.app;

import edu.curtin.app.enums.NewDayObserverOrder;
import edu.curtin.app.factories.TownTrackFactory;
import edu.curtin.app.file_output.FileOutput;
import edu.curtin.app.interfaces.NewDayObserver;
import edu.curtin.app.library.TownsInput;
import edu.curtin.app.railways.RailwayController;
import edu.curtin.app.simulation.SimulationRunner;
import edu.curtin.app.town_related.Town;
import edu.curtin.app.town_related.TownManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{

    public static void main(String[] args) throws IOException { //NOPMD
//ignored PMD, cannot catch exception in System.in.available() because future program execution may depend
// on this method working
        int dayCount = 1;

        TownsInput input = new TownsInput();

        //factory only produces tracks and towns
        TownTrackFactory factory = new TownTrackFactory();

        //the town manager contains all the towns, railway controllers
        TownManager townManager = new TownManager();

        //the class which is responsible for outputting to a file
        FileOutput output = new FileOutput(townManager, "simoutput.dot");

        //responsible for running the simulation
        SimulationRunner simRunner = new SimulationRunner(factory, output, townManager);
        simRunner.init();

        while (System.in.available() == 0) {

            String message;

            System.out.println("\n--------------------------------");
            System.out.println("Day: " + dayCount + "\n");
            //update all the observers, notifying them of a new day
            simRunner.notifyObservers();
            //while the message isn't null...
            while ((message = input.nextMessage()) != null) {
                simRunner.runSimulation(message);
            }
            System.out.println();

            simRunner.printTownData();

            try {

                Thread.sleep(1000);
                dayCount++;

            } catch (InterruptedException e) {
                throw new AssertionError(e);
            }
        }

    }

}
