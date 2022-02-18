package data;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Observable;

public class GameGrid extends Observable {
    public GameGrid(int size) {
    }

    public TileState getLocation(int x, int y) {
        throw new NotImplementedException();
    }

    public int getSize() {
        throw new NotImplementedException();
    }

    public boolean move(int x, int y, TileState player) {
        throw new NotImplementedException();
    }

    public void clearGrid() {
        throw new NotImplementedException();
    }

    public boolean isWinner(TileState player) {
        throw new NotImplementedException();
    }
}
