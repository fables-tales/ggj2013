package com.samphippen.games.ggj2013;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class ObstaclesFactory {
	
	private List<GameObject> mWorldObjects;
	
	public ObstaclesFactory(List<GameObject> worldObjects) {
		this.mWorldObjects = worldObjects;
	}

	public void makeObstacles(){
		for(int i =0; i <1; i++){
			Vector2 obstaclePosition = new Vector2();
			obstaclePosition.set(40, 40);
			ObstacleObject obstacle = new ObstacleObject(obstaclePosition);
			mWorldObjects.add(obstacle);
		}
	}

}
