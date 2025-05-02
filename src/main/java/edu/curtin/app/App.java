package edu.curtin.app;

import edu.curtin.app.factories.TownTrackFactory;
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

        int dayCount = 0;

        List<String> allowedKeywords = new ArrayList<>()
        {{
            add("town-founding");
            add("town-population");
            add("railway-construction");
            add("railway-duplication");
        }};

        List<NewDayObserver> newDayObservers = new ArrayList<>();

        TownsInput input = new TownsInput();

        TownTrackFactory factory = new TownTrackFactory();

        TownManager townManager = new TownManager();

        while (System.in.available() == 0) {
            try {
                dayCount++;
                String[] parts = new String[3];
                String message;
                while ((message = input.nextMessage()) != null) {
                    parts = message.split(" ");

                    if (parts.length != 3 || !allowedKeywords.contains(parts[0])) {
                        throw new IllegalArgumentException("Message must have 3 parts");
                    }
                    System.out.println(parts[0] + " " + parts[1] + " " + parts[2]);

                    switch (parts[0]) {
                        case "town-founding":
                            String townName = parts[1];
                            int population = Integer.parseInt(parts[2]);
                            Town newTown = factory.createTown(townName, population);
                            townManager.addTown(newTown, townName);
                            newDayObservers.add((NewDayObserver) newTown);
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
                            newDayObservers.add((NewDayObserver) singleTrack);
                            break;
                        }
                        case "railway-duplication":
                            Town townA = townManager.getTown(parts[1]);
                            Town townB = townManager.getTown(parts[2]);
                            RailwayController curSingleTrack = townManager.getTrackByTowns(townA, townB);
                            //curSingleTrack.transportGoods();
                            curSingleTrack.beginDoubleConstruction();
                            break;
                    }
                };

                //print the results

                try {
                    Thread.sleep(1000);

                    for (NewDayObserver newDayObserver : newDayObservers) {
                        newDayObserver.update();
                    }
                } catch (InterruptedException e) {
                    throw new AssertionError(e);
                }

                System.out.println("Day " + dayCount);

            }
            catch (IllegalArgumentException e) {
                logger.log(Level.WARNING, "Illegal message");
            }
        }

    }
}
