package com.samphippen.games.ggj2013.pathfind;

import java.util.ArrayList;
import java.util.List;

public final class Position {
    public int x, y;

    public Position() {
        this(0, 0);
    }

    public Position(int xPosition, int yPosition) {
        x = xPosition;
        y = yPosition;
    }

    @Override
    public int hashCode() {
        return x ^ y << 16;
    }

    @Override
    public boolean equals(Object otherObject) {
        try {
            Position otherPosition = (Position) otherObject;
            return otherPosition.x == x && otherPosition.y == y;
        } catch (ClassCastException notAPositionException) {
            return false;
        }
    }

    public Position offset(int xOffset, int yOffset) {
        return new Position(x + xOffset, y + yOffset);
    }

    public int distanceTo(Position otherPosition) {
        return Math.abs(x - otherPosition.x) + Math.abs(y - otherPosition.y);
    }

    public List<Position> validNeighbours(int gridWidth, int gridHeight) {
        List<Position> neighbours = new ArrayList<Position>();
        if (x > 0) {
            neighbours.add(offset(-1, 0));
        }
        if (x < gridWidth - 1) {
            neighbours.add(offset(1, 0));
        }
        if (y > 0) {
            neighbours.add(offset(0, -1));
        }
        if (y < gridHeight - 1) {
            neighbours.add(offset(0, 1));
        }
        return neighbours;
    }
}
