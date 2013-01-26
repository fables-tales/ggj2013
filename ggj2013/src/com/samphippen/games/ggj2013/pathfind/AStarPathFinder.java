package com.samphippen.games.ggj2013.pathfind;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarPathFinder implements PathFinder {

    @Override
    public List<Position> findPath(Position from, final Position to,
            GridProvider provider) {
        if (from.equals(to)) {
            List<Position> path = new ArrayList<Position>();
            path.add(from);
            return path;
        }
        int gridWidth = provider.getWidth();
        int gridHeight = provider.getHeight();
        final Map<Position, Integer> gScores = new HashMap<Position, Integer>();
        Comparator<Position> positionComparator = new Comparator<Position>() {
            @Override
            public int compare(Position left, Position right) {
                int gScoreLeft = gScores.get(left);
                int gScoreRight = gScores.get(right);
                int fScoreLeft = left.distanceTo(to);
                int fScoreRight = right.distanceTo(to);
                int totalScoreLeft = gScoreLeft + fScoreLeft;
                int totalScoreRight = gScoreRight + fScoreRight;
                if (totalScoreLeft < totalScoreRight) {
                    return -1;
                } else if (totalScoreLeft > totalScoreRight) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
        // System.err.println("- STARTING");
        Set<Position> closedSet = new HashSet<Position>();
        PriorityQueue<Position> openSet = new PriorityQueue<Position>(128,
                positionComparator);
        gScores.put(from, 0);
        openSet.add(from);
        // iteratively build the closed set
        // System.err.println("- BUILD CLOSED SET");
        while (!closedSet.contains(to)) {
            Position next;
            try {
                next = openSet.poll();
                // System.err
                // .printf("- SELECTED ELEMENT %d, %d\n", next.x, next.y);
            } catch (NoSuchElementException openSetEmptyException) {
                return null; // no path
            }
            int currentGScore = gScores.get(next);
            List<Position> neighbours = next.validNeighbours(gridWidth,
                    gridHeight);
            for (Position potentialNextPosition : neighbours) {
                if (closedSet.contains(potentialNextPosition)) {
                    continue;
                }
                if (provider.isObstacle(potentialNextPosition)) {
                    continue;
                }
                gScores.put(potentialNextPosition, currentGScore + 1);
                openSet.add(potentialNextPosition);
            }
            // System.err.printf("- ADDED %d, %d TO CLOSED SET\n", next.x,
            // next.y);
            closedSet.add(next);
        }
        // work backwards from g score table to build path
        // System.err.println("Working backwards to build path");
        final List<Position> path = new ArrayList<Position>();
        path.add(to);
        Position currentPosition = to;
        while (!currentPosition.equals(from)) {
            List<Position> neighbours = currentPosition.validNeighbours(
                    gridWidth, gridHeight);
            int currentGScore = gScores.get(currentPosition);
            for (Position potentialPrevPosition : neighbours) {
                if (!gScores.containsKey(potentialPrevPosition)) {
                    continue;
                }
                int potentialGScore = gScores.get(potentialPrevPosition);
                if (potentialGScore == currentGScore - 1) {
                    currentPosition = potentialPrevPosition;
                    path.add(currentPosition);
                    // System.err.printf("Got next point %d, %d\n",
                    // currentPosition.x, currentPosition.y);
                    break;
                }
            }
        }
        // System.err.println("done!");
        // return an adaptor which reverses the list
        return new AbstractList<Position>() {
            @Override
            public Position get(int index) {
                return path.get(path.size() - index - 1);
            }

            @Override
            public int size() {
                return path.size();
            }
        };
    }
}
