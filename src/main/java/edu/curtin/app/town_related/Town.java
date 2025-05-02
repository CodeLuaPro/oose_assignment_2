package edu.curtin.app.town_related;

import edu.curtin.app.interfaces.NewDayObserver;

public class Town implements NewDayObserver {
    private int population;
    int stockpile = 0;
    String name;

    public Town(int population, String name) {
        this.population = population;
        this.name = name;
    }

    public void addStockpile() {
        stockpile += 1 * population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void reduceStockpile(int amount) {
        if (amount < stockpile) {
            stockpile -= amount;
        }
        else {
            stockpile = 0;
        }

    }

    @Override
    public void update() {
        addStockpile();
    }
}
