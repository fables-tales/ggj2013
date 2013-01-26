package com.samphippen.games.ggj2013;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class GameServices {

    public static float WIDTH = 800;
    public static float HEIGHT = 600;

    private static long tickCount = 0;

    public static Sprite loadSprite(String spriteName) {
        return new Sprite(new Texture(Gdx.files.internal("bin/data/"
                + spriteName)));
    }

    public static void advanceTicks() {
        ++tickCount;
    }

    public static long getTicks() {
        return tickCount;
    }

    public static Random sRng = new Random();
}
