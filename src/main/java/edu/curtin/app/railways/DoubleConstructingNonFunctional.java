package edu.curtin.app.railways;

import edu.curtin.app.interfaces.RailwayState;

public class DoubleConstructingNonFunctional implements RailwayState {
    @Override
    public void transportGoods(RailwayController rc) {

    }

    @Override
    public void progressConstruction(RailwayController rc) {
        rc.setDaysToCompletion(rc.getDaysToCompletion() - 1);
        if (rc.getDaysToCompletion() <= 0) {
            rc.setState(new DoubleTransporting());
            rc.getTownA().setNumDoubleTracks(rc.getTownA().getNumDoubleTracks() + 1);
            rc.getTownB().setNumDoubleTracks(rc.getTownB().getNumDoubleTracks() + 1);
        }
    }

    @Override
    public void beginDoubleConstruction(RailwayController rc) {

    }
}
