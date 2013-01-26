package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ObstacleObject implements GameObject {

    private final Sprite mSprite;

    public ObstacleObject(Vector2 position) {
        mSprite = GameServices.loadSprite("tree.png");
        
        mSprite.setPosition(position.x, position.y);
    }

    @Override
    public void update() {
        Vector2 playerPosition = PlayerObject.getInstance().getPosition();
        if (playerPosition.dst(mSprite.getX(), mSprite.getY()) < Constants
                .getDouble("obstacle_width")) {
            mSprite.setColor(0.1f, 1, 0.1f, 1);
            PlayerObject.getInstance().rejectMovement();
            PlayerObject.getInstance().HeartBeatParameters.setHeartBeatFast();
        }
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderQueue) {
        renderQueue.add(new SpriteRenderable(mSprite), (int) mSprite.getY());
    }

}
