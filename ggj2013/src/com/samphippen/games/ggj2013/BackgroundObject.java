package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class BackgroundObject implements GameObject {

    private final List<SpriteRenderable> mSpriteRenderables = new ArrayList<SpriteRenderable>();

    private static final int K = 1000;

    public BackgroundObject() {
        Sprite sprite = GameServices.makeRepeatSprite("big.png", 256 * K,
                256 * K);
        sprite.setPosition(-256 * K / 2, -256 * K / 2);

        SpriteRenderable sr = new SpriteRenderable(sprite);
        mSpriteRenderables.add(sr);

    }

    @Override
    public void update() {

    }

    @Override
    public void emitRenderables(RenderQueueProxy renderables) {
        for (SpriteRenderable sr : mSpriteRenderables) {
            renderables.add(sr, Constants.QUITE_A_LOT);
        }

    }

}
