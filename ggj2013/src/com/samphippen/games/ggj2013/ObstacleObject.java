package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ObstacleObject implements GameObject {
	
	private Sprite mSprite;
	
	public ObstacleObject(Vector2 position)
	{
		mSprite =  GameServices.loadSprite("color.png");
		mSprite.setPosition(position.x, position.y);
	}
	
	@Override
	public void update() {
		PlayerObject player = PlayerObject.getInstance();
        Vector2 playerPosition = PlayerObject.getInstance().getPosition();
        if (playerPosition.dst(mSprite.getX(), mSprite.getY()) < mSprite
                .getWidth()) {
            mSprite.setColor(0.1f, 1, 0.1f, 1);
    		player.HeartBeatParameters.setHeartBeatFast();
        }
	}

    @Override
    public void emitRenderables(RenderQueueProxy renderQueue) {
        renderQueue.add(new SpriteRenderable(mSprite), (int) mSprite.getY());
    }

}
