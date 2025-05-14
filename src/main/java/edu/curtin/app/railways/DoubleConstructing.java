package edu.curtin.app.railways;

import edu.curtin.app.interfaces.RailwayState;
import edu.curtin.app.town_related.Town;

//PURPOSE: State class for the railway controller. Duplicating a single functional railway.
public class DoubleConstructing implements RailwayState {

    //PURPOSE: keep transporting goods to the single direction while the double
    //railway is building
    @Override
    public void transportGoods(RailwayController rc) {
        Town curTown = rc.getCurTown();
        curTown.setGoodsTransportedToday(curTown.getGoodsTransportedToday() + rc.getTransportAmount());
        curTown.reduceStockpile(rc.getTransportAmount());
        rc.switchCurTown();
    }

    //progress the construction on each day. When the construction completes, update the
    //railway count and switch state.
    @Override
    public void progressConstruction(RailwayController rc) {
        rc.setDaysToCompletion(rc.getDaysToCompletion() - 1);
        if (rc.getDaysToCompletion() <= 0) {
            rc.setState(new DoubleTransporting());

            rc.getTownA().setNumSingleTracks(rc.getTownA().getNumSingleTracks() - 1);
            rc.getTownB().setNumSingleTracks(rc.getTownB().getNumSingleTracks() - 1);

            rc.getTownA().setNumDoubleTracks(rc.getTownA().getNumDoubleTracks() + 1);
            rc.getTownB().setNumDoubleTracks(rc.getTownB().getNumDoubleTracks() + 1);

            rc.setFileOptions("[color=\"black:black\"]");
        }
    }

    @Override
    public void beginDoubleConstruction(RailwayController rc) {

    }
}
