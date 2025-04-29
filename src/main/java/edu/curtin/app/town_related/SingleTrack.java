package edu.curtin.app.town_related;

public class SingleTrack extends Railway {
    private Town currTownTurn;
    public SingleTrack(Town townA, Town townB) {
        super(townA, townB);
        currTownTurn = townA;
    }

    @Override
    public void transportGoods() {
        if (daysToCompletion == 0) {
            currTownTurn.reduceStockpile(100);
        }

    }
    public void switchCurrentTown() {
        if (currTownTurn == townA) {
            currTownTurn = townB;
        }
        else {
            currTownTurn = townA;
        }
    }
}
