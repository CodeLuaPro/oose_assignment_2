package edu.curtin.app;

import edu.curtin.app.enums.NewDayObserverOrder;
import edu.curtin.app.factories.TownTrackFactory;
import edu.curtin.app.file_output.FileOutput;
import edu.curtin.app.interfaces.NewDayObserver;
import edu.curtin.app.interfaces.NewDayObserverPriority;
import edu.curtin.app.library.TownsInput;
import edu.curtin.app.railways.RailwayController;
import edu.curtin.app.town_related.Town;
import edu.curtin.app.town_related.TownManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App
{
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws IOException { //NOPMD

        int dayCount = 1;

        List<String> allowedKeywords = new ArrayList<>()
        {{
            add("town-founding");
            add("town-population");
            add("railway-construction");
            add("railway-duplication");
        }};

        List<List<NewDayObserver>> newDayObservers = new ArrayList<>();

        for (int i = 0; i < NewDayObserverOrder.values().length; i++) {
            newDayObservers.add(new ArrayList<>());
        }


        TownsInput input = new TownsInput();

        TownTrackFactory factory = new TownTrackFactory();

        TownManager townManager = new TownManager();

        FileOutput output = new FileOutput(townManager, "simoutput.dot");
        newDayObservers.get(NewDayObserverOrder.POST.ordinal()).add(output);

        while (System.in.available() == 0) {

            String[] parts = new String[3];
            String message;

            System.out.println("\n--------------------------------");
            System.out.println("Day: " + dayCount + "\n");

            while ((message = input.nextMessage()) != null) {
                parts = message.split(" ");

                if (parts.length != 3 || !allowedKeywords.contains(parts[0])
                        || (parts[0].contains("town-population") && (townManager.getTown(parts[1]) == null))
                        || (parts[0].contains("railway") && (townManager.getTown(parts[2]) == null || townManager.getTown(parts[1]) == null))) {
                    logger.log(Level.WARNING, "Illegal message");
                }

                else {
                    System.out.println(parts[0] + " " + parts[1] + " " + parts[2]);

                    switch (parts[0]) {
                        case "town-founding":
                            String townName = parts[1];
                            int population = Integer.parseInt(parts[2]);
                            Town newTown = factory.createTown(townName, population);
                            townManager.addTown(newTown, townName);

                            newDayObservers.get(NewDayObserverOrder.PRE.ordinal()).add(newTown);
                            break;
                        case "town-population":
                            Town town = townManager.getTown(parts[1]);
                            town.setPopulation(Integer.parseInt(parts[2]));
                            break;
                        case "railway-construction":
                        {
                            Town townA = townManager.getTown(parts[1]);
                            Town townB = townManager.getTown(parts[2]);

                            RailwayController singleTrack = factory.createRailwayController(townA, townB);
                            townManager.addTrack(singleTrack);
                            newDayObservers.get(NewDayObserverOrder.DURING.ordinal()).add(singleTrack);
                            break;
                        }
                        case "railway-duplication":
                            Town townA = townManager.getTown(parts[1]);
                            Town townB = townManager.getTown(parts[2]);

                            RailwayController curSingleTrack = townManager.getTrackByTowns(townA, townB);
                            curSingleTrack.beginDoubleConstruction();
                            break;
                    }
                }


            }
            System.out.println();

            List<Town> townsList = townManager.getTownsAsList();
            List<RailwayController> tracksList = townManager.getTracksAsList();

            for (Town town : townsList) {
                int singleTracks = town.getNumSingleTracks();
                int doubleTracks = town.getNumDoubleTracks();
                int population = town.getPopulation();
                int stockpile = town.getStockpile();
                int goodsTransporting = town.getGoodsTransportedToday();

                String townName = town.getName();
                System.out.println(townName + " p:" + town.getPopulation() + " rs:" + singleTracks + " rd:" + doubleTracks
                 + " gs:" + stockpile + " gt:" + goodsTransporting);
            }

            try {
                for (List<NewDayObserver> newDayObserverSublist : newDayObservers) {
                    for (NewDayObserver newDayObserverObj : newDayObserverSublist) {
                        newDayObserverObj.update();
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
