package edu.curtin.app.railways;

import edu.curtin.app.interfaces.RailwayState;
import edu.curtin.app.town_related.Town;

public class DoubleTransporting implements RailwayState {

    @Override
    public void transportGoods(RailwayController rc) {
        Town townA = rc.getTownA();
        Town townB = rc.getTownB();
        townA.reduceStockpile(rc.getTransportAmount());
        townB.reduceStockpile(rc.getTransportAmount());
    }

    @Override
    public void progressConstruction(RailwayController rc) {

    }

    @Override
    public void beginDoubleConstruction(RailwayController rc) {

    }
}
