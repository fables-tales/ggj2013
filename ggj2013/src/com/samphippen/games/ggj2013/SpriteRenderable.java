package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteRenderable implements Renderable {
    
    private Sprite mSprite;
    
    public SpriteRenderable(String fileName) {
        mSprite = GameServices.loadSprite("color.png");
    }

    @Override
    public void draw(SpriteBatch sb) {
        mSprite.draw(sb);
    }
    
    
}
