package edu.curtin.app.town_related;

public abstract class Railway {
    protected Town townA;
    protected Town townB;
    protected int count = 1;
    protected int daysToCompletion = 5;
    public Railway(Town townA, Town townB) {
        this.townA = townA;
        this.townB = townB;

    }
    public void progressConstruction() {
        if (daysToCompletion > 0) {
            daysToCompletion--;
        }
    }

    public abstract void transportGoods();
}
