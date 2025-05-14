package edu.curtin.app.town_related;

import edu.curtin.app.interfaces.NewDayObserver;

import java.util.logging.Level;
import java.util.logging.Logger;

//PURPOSE: holds information about a town
public class Town implements NewDayObserver {
    private static final Logger logger = Logger.getLogger(Town.class.getName());

    private int population;
    private int stockpile = 0;
    private String name;
    private int numSingleTracks = 0;
    private int numDoubleTracks = 0;
    private int goodsTransportedToday = 0;

    public Town(int population, String name) {
        this.population = population;
        this.name = name;
    }

    public void addStockpile() {
        stockpile += 1 * population;
    }

    public void setPopulation(int population) {
        if (population < 0) {
            logger.log(Level.WARNING, "population is negative");
            throw new IllegalArgumentException("Population must be a positive number");
        }
        this.population = population;
    }

    public void reduceStockpile(int amount) {
        if (amount < 0) {
            logger.log(Level.WARNING, "amount is negative");
            throw new IllegalArgumentException("Amount to reduce by must be a positive number");
        }
        if (amount < stockpile) { //try to subtract the amount without going into the negatives
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
        if (goodsTransportedToday < 0) {
            logger.log(Level.WARNING, "goodsTransportedToday is negative");
            throw new IllegalArgumentException("Goods transported today must be a positive number");
        }
        this.goodsTransportedToday = goodsTransportedToday;
    }

    //at the start of each day, reset the goods transported on that day and fill the stockpile
    @Override
    public void update() {
        setGoodsTransportedToday(0);
        addStockpile();
    }
}
