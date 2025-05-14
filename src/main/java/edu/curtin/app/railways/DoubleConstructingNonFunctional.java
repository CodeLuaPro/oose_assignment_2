package edu.curtin.app.railways;

import edu.curtin.app.interfaces.RailwayState;

//PURPOSE: State class for the railway controller. Duplicating a single railway thats still under construction
public class DoubleConstructingNonFunctional implements RailwayState {
    @Override
    public void transportGoods(RailwayController rc) {

    }

    //PURPOSE: progress construction. When the construction completes, update the # of double tracks, switch state
    @Override
    public void progressConstruction(RailwayController rc) {
        rc.setDaysToCompletion(rc.getDaysToCompletion() - 1);
        if (rc.getDaysToCompletion() <= 0) {
            rc.setState(new DoubleTransporting());
            rc.getTownA().setNumDoubleTracks(rc.getTownA().getNumDoubleTracks() + 1);
            rc.getTownB().setNumDoubleTracks(rc.getTownB().getNumDoubleTracks() + 1);

            rc.setFileOptions("[color=\"black:black\"]");
        }
    }

    @Override
    public void beginDoubleConstruction(RailwayController rc) {

    }
}
