package com.samphippen.games.ggj2013;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class GameServices {

    public static float WIDTH = 800;
    public static float HEIGHT = 600;
    public static float PATH_FINDER_WIDTH = 8000;
    public static float PATH_FINDER_HEIGHT = 6000;

    private static long tickCount = 0;

    public static Vector2 toPathFinder(Vector2 realPosition) {
        return new Vector2(realPosition).add(PATH_FINDER_WIDTH / 2,
                PATH_FINDER_WIDTH / 2);
    }

    public static Vector2 fromPathFinder(Vector2 pathFinderPosition) {
        return new Vector2(pathFinderPosition).sub(PATH_FINDER_WIDTH / 2,
                PATH_FINDER_WIDTH / 2);
    }

    public static Sprite loadSprite(String spriteName) {
        return new Sprite(new Texture(Gdx.files.internal("bin/data/"
                + spriteName)));
    }

    public static Sprite makeRepeatSprite(String spriteName, int width,
            int height) {
        Texture t = new Texture(Gdx.files.internal("bin/data/" + spriteName));
        t.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
        Sprite s = new Sprite(t);
        s.setRegion(0, 0, width, height);
        s.setSize(width, height);
        return s;
    }

    public static void advanceTicks() {
        ++tickCount;
    }

    public static long getTicks() {
        return tickCount;
    }

    public static Random sRng = new Random();
}
