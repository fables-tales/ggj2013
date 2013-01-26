package com.samphippen.games.ggj2013.pathfind;

public interface GridProvider {
    public int getWidth();

    public int getHeight();

    public boolean isObstacle(Position pos);
}
