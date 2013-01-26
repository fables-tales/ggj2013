package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class BackgroundObject implements GameObject {

    private final SpriteRenderable mSpriteRenderable;

    public BackgroundObject() {
        Sprite sprite = GameServices.loadSprite("newBackground.png");
        sprite.setPosition(-400, -300);
        mSpriteRenderable = new SpriteRenderable(sprite);
    }

    @Override
    public void update() {

    }

    @Override
    public void emitRenderables(RenderQueueProxy renderables) {
        renderables.add(mSpriteRenderable);
    }

}
