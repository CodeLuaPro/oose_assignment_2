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
        townA.setGoodsTransportedToday(townA.getGoodsTransportedToday() + 100);
        townB.setGoodsTransportedToday(townB.getGoodsTransportedToday() + 100);
    }

    @Override
    public void progressConstruction(RailwayController rc) {

    }

    @Override
    public void beginDoubleConstruction(RailwayController rc) {

    }
}
