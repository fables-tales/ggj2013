package com.samphippen.games.ggj2013;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameServices {
    
    private GameServices() {
        
    }
    
    public static Sprite loadSprite(String spriteName) {
        Sprite s = new Sprite(new Texture(Gdx.files.internal("bin/data/" + spriteName)));
        return s;
    }
    
    public static Random sRng = new Random();
}
