package edu.curtin.app.railways;

import edu.curtin.app.interfaces.RailwayState;
import edu.curtin.app.town_related.Town;

public class SingleTransporting implements RailwayState {

    @Override
    public void transportGoods(RailwayController rc) {
        Town curTown = rc.getCurTown();
        curTown.reduceStockpile(rc.getTransportAmount());
        rc.switchCurTown();
    }

    @Override
    public void progressConstruction(RailwayController rc) {

    }

    @Override
    public void beginDoubleConstruction(RailwayController rc) {
        rc.setState(new DoubleConstructing());
    }
}
