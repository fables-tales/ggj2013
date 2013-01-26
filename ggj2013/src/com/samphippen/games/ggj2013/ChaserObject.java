package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ChaserObject implements GameObject {

    private Sprite mSprite;

    private Vector2 mPosition;

    private int mOutOfLight;

    public ChaserObject() {
        mSprite = GameServices.loadSprite("chaser.png");
        mPosition = new Vector2(80, 80);
    }

    @Override
    public void update() {
        PlayerObject player = PlayerObject.getInstance();
		Vector2 playerPosition = player.getPosition();

        Vector2 delta = new Vector2(playerPosition).sub(mPosition);
        if (delta.len() > 400) {
            mOutOfLight++;
        } else if (GameServices.getTicks() % 1 == 0) {
            mPosition.add(delta.mul(0.01f));
        }
        if (mOutOfLight >= 2 * 60 + GameServices.sRng.nextInt(60 * 8)) {
            mOutOfLight = 0;
            float theta = (float) (GameServices.sRng.nextFloat() * Math.PI * 2);
            float radius = GameServices.sRng.nextFloat() * 200 + 20;
            float x = (float) ((float) radius * Math.cos(theta));
            float y = (float) ((float) radius * Math.sin(theta));
            Vector2 position = new Vector2(playerPosition).add(x, y);
            mPosition = position;
        }
        // Make heartbeat fast if near
        if(mPosition.dst(playerPosition) < Constants.sConstants.get("chaser_heart_attack_distance_trigger") ){
        	player.HeartBeatParameters.setHeartBeatFast();
        }

        mSprite.setPosition(mPosition.x, mPosition.y);

    }

    @Override
    public void emitRenderables(RenderQueueProxy renderQueue) {
        renderQueue.add(new SpriteRenderable(mSprite), (int)mPosition.y);
    }

    public Vector2 getPosition() {
        return mPosition;
    }

}
