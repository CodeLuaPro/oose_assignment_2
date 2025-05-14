package edu.curtin.app.railways;

import edu.curtin.app.interfaces.RailwayState;
import edu.curtin.app.town_related.Town;

//PURPOSE: State class for the railway controller. A finished double railway
public class DoubleTransporting implements RailwayState {

    //PURPOSE: Transport the goods in both directions.
    @Override
    public void transportGoods(RailwayController rc) {
        Town townA = rc.getTownA();
        Town townB = rc.getTownB();
        townA.reduceStockpile(rc.getTransportAmount());
        townB.reduceStockpile(rc.getTransportAmount());
        townA.setGoodsTransportedToday(townA.getGoodsTransportedToday() + rc.getTransportAmount());
        townB.setGoodsTransportedToday(townB.getGoodsTransportedToday() + rc.getTransportAmount());
    }

    @Override
    public void progressConstruction(RailwayController rc) {

    }

    @Override
    public void beginDoubleConstruction(RailwayController rc) {

    }
}
