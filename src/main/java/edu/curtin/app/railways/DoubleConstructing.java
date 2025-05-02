package edu.curtin.app.railways;

import edu.curtin.app.interfaces.RailwayState;
import edu.curtin.app.town_related.Town;

public class DoubleConstructing implements RailwayState {

    @Override
    public void transportGoods(RailwayController rc) {
        Town curTown = rc.getCurTown();
        curTown.reduceStockpile(rc.getTransportAmount());
        rc.switchCurTown();
    }

    @Override
    public void progressConstruction(RailwayController rc) {
        rc.setDaysToCompletion(rc.getDaysToCompletion() - 1);
        if (rc.getDaysToCompletion() <= 0) {
            rc.setState(new SingleTransporting());

        }
    }

    @Override
    public void beginDoubleConstruction(RailwayController rc) {

    }
}
