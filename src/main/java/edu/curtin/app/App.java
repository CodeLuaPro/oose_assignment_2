package edu.curtin.app;

import edu.curtin.app.enums.NewDayObserverOrder;
import edu.curtin.app.factories.TownTrackFactory;
import edu.curtin.app.file_output.FileOutput;
import edu.curtin.app.interfaces.NewDayObserver;
import edu.curtin.app.library.TownsInput;
import edu.curtin.app.railways.RailwayController;
import edu.curtin.app.town_related.Town;
import edu.curtin.app.town_related.TownManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws IOException { //NOPMD
//ignored PMD, cannot catch exception in System.in.available() because future program execution may depend
// on this method working
        int dayCount = 1;

        //The allowed first command in the messages
        List<String> allowedKeywords = new ArrayList<>()
        {{
            add("town-founding");
            add("town-population");
            add("railway-construction");
            add("railway-duplication");
        }};

        //create a list of observers with multiple levels
        List<List<NewDayObserver>> newDayObservers = new ArrayList<>();

        //3 levels: PRE, DURING, POST.
        //e.g. want towns to call update() before railway controllers, so we put towns in the PRE list
        //and railways controllers in the DURING list. Allows order of execution.
        for (int i = 0; i < NewDayObserverOrder.values().length; i++) {
            newDayObservers.add(new ArrayList<>());
        }


        TownsInput input = new TownsInput();


        //factory only produces tracks and towns
        TownTrackFactory factory = new TownTrackFactory();

        //the town manager contains all the towns, railway controllers
        TownManager townManager = new TownManager();

        FileOutput output = new FileOutput(townManager, "simoutput.dot");
        //put the output object in the last list.
        newDayObservers.get(NewDayObserverOrder.POST.ordinal()).add(output);

        while (System.in.available() == 0) {

            String[] parts;
            String message;

            System.out.println("\n--------------------------------");
            System.out.println("Day: " + dayCount + "\n");
            //while the message isn't null...
            while ((message = input.nextMessage()) != null) {
                parts = message.split(" ");

                //check for incorrect messages. Ignore any incorrect messages.
                if (parts.length != 3 || !allowedKeywords.contains(parts[0])
                        || (parts[0].contains("town-population") && (townManager.getTown(parts[1]) == null))
                        || (parts[0].contains("railway") && (townManager.getTown(parts[2]) == null || townManager.getTown(parts[1]) == null))
                        || (parts[0].contains("town") && Integer.parseInt(parts[2]) < 0)
                        || (parts[0].contains("railway") && (townManager.getTown(parts[1]) == null || townManager.getTown(parts[2]) == null))
                ) {
                    logger.log(Level.WARNING, "Illegal message");
                }
                else { //message is correct...
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
                            newDayObservers.get(NewDayObserverOrder.PRE.ordinal()).add(newTown);
                            break;
                        case "town-population":
                            logger.log(Level.INFO, "Town population");
                            Town town = townManager.getTown(parts[1]);
                            town.setPopulation(Integer.parseInt(parts[2]));
                            break;
                        case "railway-construction":
                        {
                            logger.log(Level.INFO, "railway construction");
                            Town townA = townManager.getTown(parts[1]);
                            Town townB = townManager.getTown(parts[2]);

                            RailwayController singleTrack = factory.createRailwayController(townA, townB);
                            townManager.addTrack(singleTrack);
                            //add railway controllers to the middle list of observers
                            newDayObservers.get(NewDayObserverOrder.DURING.ordinal()).add(singleTrack);
                            break;
                        }
                        case "railway-duplication":
                            logger.log(Level.INFO, "railway duplication");
                            Town townA = townManager.getTown(parts[1]);
                            Town townB = townManager.getTown(parts[2]);

                            RailwayController curSingleTrack = townManager.getTrackByTowns(townA, townB);
                            curSingleTrack.beginDoubleConstruction();
                            break;
                        default:
                            logger.log(Level.WARNING, "Illegal message");
                            break;
                    }
                }

            }
            System.out.println();

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

            try {
                //update all the observers, notifying them of a new day
                for (List<NewDayObserver> newDayObserverSublist : newDayObservers) {
                    for (NewDayObserver newDayObserverObj : newDayObserverSublist) {
                        newDayObserverObj.update();
                        logger.log(Level.INFO, "observers are notified of a new day");
                    }
                }
                Thread.sleep(1000);
                dayCount++;


            } catch (InterruptedException e) {
                throw new AssertionError(e);
            }
        }

    }

    
}
