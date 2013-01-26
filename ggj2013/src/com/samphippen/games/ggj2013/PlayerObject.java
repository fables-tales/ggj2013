package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlayerObject implements GameObject {

    private static PlayerObject sInstance = null;

    private Sprite mSprite = null;

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

    }

    @Override
    public void emitRenderables(RenderQueueProxy renderables) {
        renderables.add(new SpriteRenderable(mSprite));
    }

}
