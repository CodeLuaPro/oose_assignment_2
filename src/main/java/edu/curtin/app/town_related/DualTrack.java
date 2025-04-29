package edu.curtin.app.town_related;

public class DualTrack extends Railway {

    public DualTrack(Town townA, Town townB) {
        super(townA, townB);
    }

    @Override
    public void transportGoods() {
        if (daysToCompletion == 0) {
            townA.reduceStockpile(100);
            townB.reduceStockpile(100);
        }

    }
}
