package edu.curtin.app.railways;

import edu.curtin.app.interfaces.RailwayState;
import edu.curtin.app.town_related.Town;

import java.util.logging.Logger;

//PURPOSE: State class for railway controller. Constructing a single railway.
public class SingleConstructing implements RailwayState {

    @Override
    public void transportGoods(RailwayController rc) {

    }

    //PURPOSE: progress the construction of the railway. When the construction completes,
    //update railway info and switch state
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

    //PURPOSE: Transition straight to double railway without waiting for a single railway to complete.
    //In that case, reset the counter on the # of days to finish construction and transition to double bulding
    //state.
    @Override
    public void beginDoubleConstruction(RailwayController rc) {
        rc.setDaysToCompletion(5);
        rc.setState(new DoubleConstructingNonFunctional());
        rc.setFileOptions("[style=\"dashed\",color=\"black:black\"]");
    }
}
