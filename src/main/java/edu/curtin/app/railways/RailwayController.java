package edu.curtin.app.railways;

import edu.curtin.app.interfaces.NewDayObserver;
import edu.curtin.app.interfaces.RailwayState;
import edu.curtin.app.town_related.Town;

public class RailwayController implements NewDayObserver {
    private Town townA;
    private Town townB;
    private Town curTown;
    private RailwayState state = new SingleConstructing();
    private int transportAmount = 100;
    private int daysToCompletion = 5;

    public RailwayController(Town townA, Town townB) {
        this.townA = townA;
        this.townB = townB;
        curTown = townA;
    }

    public void transportGoods() {
        state.transportGoods(this);
    }

    public void progressConstruction() {
        state.progressConstruction(this);
    }

    public void beginDoubleConstruction() {
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

    public void setCurTown(Town curTown) {
        this.curTown = curTown;
    }

    public RailwayState getState() {
        return state;
    }

    public int getTransportAmount() {
        return transportAmount;
    }

    @Override
    public void update() {
        progressConstruction();
        transportGoods();
    }
}
