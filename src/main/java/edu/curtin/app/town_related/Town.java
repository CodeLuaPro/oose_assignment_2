package edu.curtin.app.town_related;

public class Town {
    private int population;
    int stockpile = 0;

    public Town(int population) {
        this.population = population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
