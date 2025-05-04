package edu.curtin.app.railways;

import edu.curtin.app.interfaces.RailwayState;

public class SingleConstructing implements RailwayState {

    @Override
    public void transportGoods(RailwayController rc) {

    }

    @Override
    public void progressConstruction(RailwayController rc) {
        rc.setDaysToCompletion(rc.getDaysToCompletion() - 1);
        if (rc.getDaysToCompletion() <= 0) {
            rc.setState(new SingleTransporting());
            rc.setDaysToCompletion(5);

            rc.getTownA().setNumSingleTracks(rc.getTownA().getNumSingleTracks() + 1);
            rc.getTownB().setNumSingleTracks(rc.getTownB().getNumSingleTracks() + 1);

            rc.setFileOptions("");
        }
    }

    @Override
    public void beginDoubleConstruction(RailwayController rc) {
        rc.setDaysToCompletion(5);
        rc.setState(new DoubleConstructingNonFunctional());
        rc.setFileOptions("[style=\"dashed\",color=\"black:black\"]");
    }
}
