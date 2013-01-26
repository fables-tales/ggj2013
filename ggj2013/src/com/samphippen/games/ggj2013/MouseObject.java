package com.samphippen.games.ggj2013;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MouseObject implements GameObject {

    private final Sprite mSprite;

    public MouseObject() {
        mSprite = GameServices.loadSprite("mouse.png");
    }

    @Override
    public void update() {
        mSprite.setPosition(Gdx.input.getX() - GameServices.WIDTH / 2,
                GameServices.HEIGHT / 2 - Gdx.input.getY());
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderQueue) {
        renderQueue.add(new SpriteRenderable(mSprite), Constants.QUITE_A_LOT);
    }

    private static MouseObject sInstance = null;

    public static MouseObject getInstance() {
        if (sInstance == null) {
            sInstance = new MouseObject();
        }
        return sInstance;
    }

    public void draw(SpriteBatch mBatch) {
        mSprite.draw(mBatch);
    }

}
