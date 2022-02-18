package gui;

import data.GameGrid;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Observable;
import java.util.Observer;

public class GamePanel implements Observer {
    public GamePanel(GameGrid grid) {

    }

    public int[] getGridPosition(int x, int y){
        throw new NotImplementedException();
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
