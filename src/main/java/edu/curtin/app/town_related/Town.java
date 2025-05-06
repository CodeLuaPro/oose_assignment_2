package edu.curtin.app.town_related;

import edu.curtin.app.interfaces.NewDayObserver;

public class Town implements NewDayObserver {
    private int population;
    int stockpile = 0;
    String name;
    int numSingleTracks = 0;
    int numDoubleTracks = 0;
    int goodsTransportedToday = 0;

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

    public int getPopulation() {
        return population;
    }

    public int getStockpile() {
        return stockpile;
    }

    public int getNumSingleTracks() {
        return numSingleTracks;
    }

    public int getNumDoubleTracks() {
        return numDoubleTracks;
    }

    public void setNumSingleTracks(int numSingleTracks) {
        this.numSingleTracks = numSingleTracks;
    }

    public void setNumDoubleTracks(int numDoubleTracks) {
        this.numDoubleTracks = numDoubleTracks;
    }

    public String getName() {
        return name;
    }

    public int getGoodsTransportedToday() {
        return goodsTransportedToday;
    }

    public void setGoodsTransportedToday(int goodsTransportedToday) {
        this.goodsTransportedToday = goodsTransportedToday;
    }


    @Override
    public void update() {
        setGoodsTransportedToday(0);
        addStockpile();
    }
}
