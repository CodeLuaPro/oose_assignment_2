package edu.curtin.app.town_related;

public class Town {
    private int population;
    int stockpile = 0;
    String name;

    public Town(int population, String name) {
        this.population = population;
        this.name = name;
    }

    public void addStockpile() {
        stockpile = 1 * population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void reduceStockpile(int amount) {
        stockpile -= amount;
    }
}
