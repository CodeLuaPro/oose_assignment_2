package edu.curtin.app.railways;

import edu.curtin.app.interfaces.RailwayState;
import edu.curtin.app.town_related.Town;

//PURPOSE: State class for railway controller. A complete single railway
public class SingleTransporting implements RailwayState {

    //PURPOSE: transport the goods in one direction at a time
    @Override
    public void transportGoods(RailwayController rc) {
        Town curTown = rc.getCurTown();
        curTown.setGoodsTransportedToday(curTown.getGoodsTransportedToday() + rc.getTransportAmount());
        curTown.reduceStockpile(rc.getTransportAmount());
        rc.switchCurTown();
    }

    @Override
    public void progressConstruction(RailwayController rc) {

    }

    //PURPOSE: begin the construction of a double railway
    @Override
    public void beginDoubleConstruction(RailwayController rc) {
        rc.setState(new DoubleConstructing());
        rc.setFileOptions("[style=\"dashed\",color=\"black:black\"]");
    }
}
