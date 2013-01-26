package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ObstacleObject implements GameObject {

	private double PLAYER_DISTANCE_FROM_OBSTRUCTION = Constants.sConstants.get("PLAYER_DISTANCE_FROM_OBSTRUCTION");
	private Sprite mSprite = GameServices.loadSprite("color.png");
	
	public ObstacleObject(Vector2 position)
	{
		mSprite.setColor(255, 255, 1, 255);
		mSprite.setOrigin(position.x, position.y);
	}
	
	@Override
	public void update() {
        mSprite.setColor(255, 1, 1, 255);
        Vector2 playerPosition = PlayerObject.getInstance().getPosition();
        if(playerPosition.dst(mSprite.getOriginX(), mSprite.getOriginY()) < PLAYER_DISTANCE_FROM_OBSTRUCTION){
    		mSprite.setColor(255, 1, 1, 255);
        }
	}

	@Override
	public void emitRenderables(RenderQueueProxy renderQueue) {
		renderQueue.add(new SpriteRenderable(mSprite));
	}

}
