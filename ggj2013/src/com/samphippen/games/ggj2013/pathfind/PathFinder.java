package com.samphippen.games.ggj2013.pathfind;

import java.util.List;

public interface PathFinder {
    public List<Position> findPath(Position from, Position to,
            GridProvider provider);
}
