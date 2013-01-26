package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class BackgroundObject implements GameObject {

    private final List<SpriteRenderable> mSpriteRenderables = new ArrayList<SpriteRenderable>();

    public BackgroundObject() {
        for (int i = 0; i < 10; i++) {
            float x = 256 * i - 400 - 256 * 5;
            for (int j = 0; j < 10; j++) {
                float y = 256 * j - 300 - 256 * 5;
                Sprite sprite = GameServices.loadSprite("dat-background.png");
                sprite.setPosition(x, y);
                SpriteRenderable sr = new SpriteRenderable(sprite);
                mSpriteRenderables.add(sr);
            }
        }

    }

    @Override
    public void update() {

    }

    @Override
    public void emitRenderables(RenderQueueProxy renderables) {
        for (SpriteRenderable sr : mSpriteRenderables) {
            renderables.add(sr, -Constants.QUITE_A_LOT);
        }

    }

}
