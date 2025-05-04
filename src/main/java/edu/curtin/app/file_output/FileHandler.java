package edu.curtin.app.file_output;

import edu.curtin.app.interfaces.NewDayObserver;
import edu.curtin.app.town_related.TownManager;

public class FileHandler implements NewDayObserver {
    private TownManager townManager;
    public FileHandler(TownManager townManager) {
        this.townManager = townManager;
    }
    @Override
    public void update() {

    }
}
