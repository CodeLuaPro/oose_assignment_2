package edu.curtin.app.interfaces;

import edu.curtin.app.railways.RailwayController;

public interface RailwayState {
    public void transportGoods(RailwayController rc);
    public void progressConstruction(RailwayController rc);
    public void beginDoubleConstruction(RailwayController rc);
}
