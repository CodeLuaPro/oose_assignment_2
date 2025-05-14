package edu.curtin.app.railways;

import edu.curtin.app.interfaces.NewDayObserver;
import edu.curtin.app.interfaces.RailwayState;
import edu.curtin.app.town_related.Town;

import java.util.logging.Level;
import java.util.logging.Logger;

//PURPOSE: context class for the railway states
public class RailwayController implements NewDayObserver {
    private static final Logger logger = Logger.getLogger(RailwayController.class.getName());

    private Town townA;
    private Town townB;
    private Town curTown; //the town that the railway is currently transporting to (if single)
    private RailwayState state = new SingleConstructing();
    private int transportAmount = 100;
    private int daysToCompletion = 5;
    private String fileOptions = "[style=\"dashed\"]"; //options for printing the railway status to the file

    public RailwayController(Town townA, Town townB) {
        this.townA = townA;
        this.townB = townB;
        curTown = townA;
    }

    public void transportGoods() {
        logger.log(Level.INFO, "transporting goods");
        state.transportGoods(this);
    }

    public void progressConstruction() {
        logger.log(Level.INFO, "progressing construction");
        state.progressConstruction(this);
    }

    public void beginDoubleConstruction() {
        logger.log(Level.INFO, "beginning double construction");
        state.beginDoubleConstruction(this);
    }

    public void switchCurTown() {
        if (curTown.equals(townA)) {
            curTown = townB;
        }
        else {
            curTown = townA;
        }
    }

    public void setState(RailwayState state) {
        logger.log(Level.INFO, "Setting new state to railway");
        this.state = state;
    }

    public Town getTownA() {
        return townA;
    }

    public Town getTownB() {
        return townB;
    }

    public Town getCurTown() {
        return curTown;
    }

    public int getDaysToCompletion() {
        return daysToCompletion;
    }

    public void setDaysToCompletion(int daysToCompletion) {
        this.daysToCompletion = daysToCompletion;
    }

    public int getTransportAmount() {
        return transportAmount;
    }

    public String getFileOptions() {
        return fileOptions;
    }

    public void setFileOptions(String fileOptions) {
        this.fileOptions = fileOptions;
    }

    //on each day, attempt to progress construction and transport goods.
    @Override
    public void update() {
        progressConstruction();
        transportGoods();
    }
}
