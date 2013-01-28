package com.samphippen.games.ggj2013.pathfind;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;

public class ContinuousPathFinder {
    private final PathFinder mPathFinder;
    private final float mWorldWidth;
    private final float mWorldHeight;

    private final int GRID_WIDTH = 200;
    private final int GRID_HEIGHT = 200;

    private final Set<Position> mObstacleCells = new HashSet<Position>();

    public ContinuousPathFinder(PathFinder basePathFinder, float worldWidth,
            float worldHeight) {
        mPathFinder = basePathFinder;
        mWorldWidth = worldWidth;
        mWorldHeight = worldHeight;
    }

    public void addObstacle(Vector2 position, float radius) {
        float minX = position.x - radius;
        float maxX = position.x + radius;
        float minY = position.y - radius;
        float maxY = position.y + radius;
        maxX += mWorldWidth / GRID_WIDTH;
        maxY += mWorldHeight / GRID_HEIGHT;
        Position lowerLeft = nearestCell(new Vector2(minX, minY));
        Position upperRight = nearestCell(new Vector2(maxX, maxY));
        for (int x = lowerLeft.x; x <= upperRight.x; ++x) {
            for (int y = lowerLeft.y; y <= upperRight.y; ++y) {
                Vector2 centre = cellCentre(new Position(x, y));
                if (centre.dst(position) > radius) {
                    continue;
                }
                mObstacleCells.add(new Position(x, y));
            }
        }
    }

    public List<Vector2> findPath(Vector2 from, Vector2 to) {
        List<Position> discretePath = mPathFinder.findPath(nearestCell(from),
                nearestCell(to), getProvider());
        if (discretePath == null) {
            return null;
        }
        List<Vector2> continuousPath = new ArrayList<Vector2>();
        continuousPath.add(from);
        for (Position discretePosition : discretePath) {
            continuousPath.add(cellCentre(discretePosition));
        }
        continuousPath.add(to);
        return continuousPath;
    }

    private Position nearestCell(Vector2 continuousPosition) {
        float approximateX = GRID_WIDTH * continuousPosition.x / mWorldWidth;
        float approximateY = GRID_HEIGHT * continuousPosition.y / mWorldHeight;
        return new Position((int) approximateX, (int) approximateY);
    }

    private Vector2 cellCentre(Position discretePosition) {
        float approximateX = mWorldWidth * discretePosition.x / GRID_WIDTH;
        float approximateY = mWorldHeight * discretePosition.y / GRID_HEIGHT;
        // offset to get cell centre
        approximateX += mWorldWidth / (2 * GRID_WIDTH);
        approximateY += mWorldHeight / (2 * GRID_HEIGHT);
        return new Vector2(approximateX, approximateY);
    }

    private GridProvider getProvider() {
        return new GridProvider() {
            @Override
            public int getWidth() {
                return GRID_WIDTH;
            }

            @Override
            public int getHeight() {
                return GRID_HEIGHT;
            }

            @Override
            public boolean isObstacle(Position x) {
                return mObstacleCells.contains(x);
            }
        };
    }
}
