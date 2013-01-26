package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class PlayerObject implements GameObject {

    private static PlayerObject sInstance = null;

    private Sprite mSprite = null;

    public float mHeartbeatRadius = (float) 1.0;

    private final Vector2 mPosition = new Vector2(0, 0);
    private int mTicks = 0;

    private PlayerObject() {
        mSprite = GameServices.loadSprite("color.png");
    }

    public static PlayerObject getInstance() {
        if (sInstance == null) {
            sInstance = new PlayerObject();
        }

        return sInstance;
    }

    @Override
    public void update() {
        mPosition.add(InputSystem.mouseSpeedVector());
        mSprite.setPosition(mPosition.x, mPosition.y);
        mTicks++;
        mHeartbeatRadius = 300 * mTicks / 100.0f;
        mTicks = mTicks % 100;
        if (mTicks == 0) {
            GameHolder.getInstance().getSoundManager().beatHeart();
        }
    }

    public Vector2 getPosition() {
        return mPosition;
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderables) {
        renderables.add(new SpriteRenderable(mSprite), (int) mPosition.y);
    }

}
