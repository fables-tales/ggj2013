package com.samphippen.games.ggj2013;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.samphippen.games.ggj2013.pathfind.ContinuousPathFinder;

public class ObstaclesFactory {

    private final List<GameObject> mWorldObjects;
    private final ContinuousPathFinder mCpf;

    private final float OBSTACLE_FIELD_SIZE = 4000.0f;

    public ObstaclesFactory(List<GameObject> worldObjects,
            ContinuousPathFinder cpf) {
        mWorldObjects = worldObjects;
        mCpf = cpf;
    }

    public void makeObstacles() {
        float density = Constants.getFloat("obstacle_density");
        for (int i = 0; i < (int) (density * OBSTACLE_FIELD_SIZE * OBSTACLE_FIELD_SIZE); i++) {
            Vector2 obstaclePosition = new Vector2();
            obstaclePosition.set(GameServices.sRng.nextFloat()
                    * OBSTACLE_FIELD_SIZE - 0.5f * OBSTACLE_FIELD_SIZE,
                    GameServices.sRng.nextFloat() * OBSTACLE_FIELD_SIZE - 0.5f
                            * OBSTACLE_FIELD_SIZE);
            ObstacleObject obstacle = new ObstacleObject(obstaclePosition);

            mWorldObjects.add(obstacle);
            mCpf.addObstacle(GameServices.toPathFinder(obstaclePosition),
                    Constants.getFloat("obstacle_width"));
        }
    }

}
