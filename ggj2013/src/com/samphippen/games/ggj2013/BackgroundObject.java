package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class BackgroundObject implements GameObject {

    private final List<SpriteRenderable> mSpriteRenderables = new ArrayList<SpriteRenderable>();

    private static int K = 100;

    public BackgroundObject() {
        Sprite sprite = GameServices.makeRepeatSprite("dat-background.png", 256*20, 256*20);
        sprite.setPosition(-256*10, -256*10);
        
        SpriteRenderable sr = new SpriteRenderable(sprite);
        mSpriteRenderables.add(sr);

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
