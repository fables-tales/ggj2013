package com.samphippen.games.ggj2013;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.samphippen.games.ggj2013.pathfind.ContinuousPathFinder;

public class ObstaclesFactory {

    private List<GameObject> mWorldObjects;
    private ContinuousPathFinder mCpf;

    public ObstaclesFactory(List<GameObject> worldObjects,
            ContinuousPathFinder cpf) {
        this.mWorldObjects = worldObjects;
        mCpf = cpf;
    }

    public void makeObstacles() {
        for (int i = 0; i < 100; i++) {
            Vector2 obstaclePosition = new Vector2();
            obstaclePosition.set(GameServices.sRng.nextFloat()*4000-2000, GameServices.sRng.nextFloat()*4000-2000);
            ObstacleObject obstacle = new ObstacleObject(obstaclePosition);
            mWorldObjects.add(obstacle);
            mCpf.addObstacle(GameServices.toPathFinder(obstaclePosition), 16);
        }
    }

}
