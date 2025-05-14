package edu.curtin.app.simulation;

import edu.curtin.app.enums.NewDayObserverOrder;
import edu.curtin.app.factories.TownTrackFactory;
import edu.curtin.app.file_output.FileOutput;
import edu.curtin.app.interfaces.NewDayObserver;
import edu.curtin.app.railways.RailwayController;
import edu.curtin.app.town_related.Town;
import edu.curtin.app.town_related.TownManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//PURPOSE: runs the simulation, checks errors, keeps track of observers
public class SimulationRunner {
    private static final Logger logger = Logger.getLogger(SimulationRunner.class.getName());
    //Multi-level list of observers to allow ordering of observers. Need to execute certain observers
    //before/after others
    private List<List<NewDayObserver>> newDayObserverList = new ArrayList<>();
    //the list of allowed keywords that can be used as the first part of the message string
    private List<String> allowedKeywords = new ArrayList<>()
    {{
        add("town-founding");
        add("town-population");
        add("railway-construction");
        add("railway-duplication");
    }};
    private TownTrackFactory factory;
    private FileOutput output;
    private TownManager townManager;

    public SimulationRunner(TownTrackFactory factory, FileOutput output, TownManager townManager) {
        this.factory = factory;
        this.output = output;
        this.townManager = townManager;
    }

    public void init() {
        //Initialise the list of observer lists and add output to the very last sublist
        for (int i = 0; i < NewDayObserverOrder.values().length; i++) {
            newDayObserverList.add(new ArrayList<>());
        }
        addObserver(output, NewDayObserverOrder.POST);
    }

    public void runSimulation(String message) {
        String[] parts = message.split(" ");
        //check for incorrect messages. Ignore any incorrect messages.
        if (invalidateMessage(parts)) {
            logger.log(Level.WARNING, "Illegal message");
        } else { //message is correct...
            System.out.println(parts[0] + " " + parts[1] + " " + parts[2]);

            //perform operations based on the kind of message
            switch (parts[0]) {
                case "town-founding":
                    logger.log(Level.INFO, "Town founding");
                    String townName = parts[1];
                    int population = Integer.parseInt(parts[2]);
                    Town newTown = factory.createTown(townName, population);
                    townManager.addTown(newTown, townName);
                    //add towns to the first list of observers
                    addObserver(newTown, NewDayObserverOrder.PRE);
                    break;
                case "town-population":
                    logger.log(Level.INFO, "Town population");
                    Town town = townManager.getTown(parts[1]);
                    town.setPopulation(Integer.parseInt(parts[2]));
                    break;
                case "railway-construction": {
                    logger.log(Level.INFO, "railway construction");
                    Town townA = townManager.getTown(parts[1]);
                    Town townB = townManager.getTown(parts[2]);

                    RailwayController singleTrack = factory.createRailwayController(townA, townB);
                    townManager.addTrack(singleTrack);
                    //add railway controllers to the middle list of observers
                    addObserver(singleTrack, NewDayObserverOrder.DURING);
                    break;
                }
                case "railway-duplication":
                    logger.log(Level.INFO, "railway duplication");
                    Town townA = townManager.getTown(parts[1]);
                    Town townB = townManager.getTown(parts[2]);
                    //get the single track between two towns and convert to double
                    RailwayController curSingleTrack = townManager.getTrackByTowns(townA, townB);
                    curSingleTrack.beginDoubleConstruction();
                    break;
                default:
                    logger.log(Level.WARNING, "Illegal message");
                    break;
            }
        }
    }

    //PURPOSE: prints the town information as part of the console output
    public void printTownData() {
        List<Town> townsList = townManager.getTownsAsList();

        //for each town founded...
        for (Town town : townsList) {
            //get information about its connected railways, stockpile etc.
            int singleTracks = town.getNumSingleTracks();
            int doubleTracks = town.getNumDoubleTracks();
            int population = town.getPopulation();
            int stockpile = town.getStockpile();
            int goodsTransporting = town.getGoodsTransportedToday();

            String townName = town.getName();
            //print information
            logger.log(Level.INFO, "Printing day info for a town");
            System.out.println(townName + " p:" + population + " rs:" + singleTracks + " rd:" + doubleTracks
                    + " gs:" + stockpile + " gt:" + goodsTransporting);
        }
    }

    //Observers are added to a level specified in the enum (PRE, DURING, POST)
    public void addObserver(NewDayObserver observer, NewDayObserverOrder order) {
        newDayObserverList.get(order.ordinal()).add(observer);
    }

    public void notifyObservers() {
        for (List<NewDayObserver> newDayObserverSublist : newDayObserverList) {
            for (NewDayObserver newDayObserverObj : newDayObserverSublist) {
                newDayObserverObj.update();
                logger.log(Level.INFO, "observers are notified of a new day");
            }
        }
    }

    //PURPOSE: Detects invalid messages
    private boolean invalidateMessage(String[] parts) {
        boolean invalid = false;

        if (parts.length == 3) {
            //if town related:
            if (parts[0].contains("town-population")) {
                try {
                    //try to get population adn the town
                    int population = Integer.parseInt(parts[2]);
                    Town town = townManager.getTown(parts[1]);
                    //if town is null or population is invalid, then the message is invalid
                    if (town == null || population < 0) {
                        invalid = true;
                    }
                }
                catch(NumberFormatException e) {
                    //usually number format exception, the population wasn't an integer
                    invalid = true;
                }
            }
            //if railway related:
            if (parts[0].contains("railway")) {
                Town townA = townManager.getTown(parts[1]);
                Town townB = townManager.getTown(parts[2]);
                //if either town is null or the same town is referenced, then invalid
                if (townA == null || townB == null || townA.equals(townB)) {
                    invalid = true;
                }

            }
            //if its town founding
            if (parts[0].contains("town-founding")) {
                try {
                    //get the population
                    int population = Integer.parseInt(parts[2]);
                    //if the population is invalid, the message is invalid
                    if (population < 0) {
                        invalid = true;
                    }
                }
                catch (NumberFormatException e) {
                    //if the population wasn't an integer, then the message is invalid
                    invalid = true;
                }
            }
            //if the first keyword isn't part of the allowed keywords, then the message is invalid
            if (!allowedKeywords.contains(parts[0])) {
                invalid = true;
            }
        }
        else {
            //discard immediately if not a 3 part message
            invalid = true;
        }

        return invalid;

    }


}
