package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SmokeObject implements GameObject {

    private List<Sprite> mSprites = new ArrayList<Sprite>();
    private Vector2 mTransform = new Vector2(0, 0);
    private int mCurrentSprite = 0;

    public SmokeObject() {
        for (int i = 0; i < 60; i++) {
            String filename = "" + i;
            while (filename.length() < 3) {
                filename = "0" + filename;
            }

            filename = "smoke_" + filename + ".png";
            Sprite s = GameServices.loadSprite(filename);
            s.setScale(3.2f);
            mSprites.add(s);
        }
    }

    public void setPosition(float x, float y) {
        mTransform.set(x, y);
    }
    
    int k = 0;

    @Override
    public void update() {
        k += 1;
        if (k % 3 == 0) {
            mCurrentSprite += 1;
        }
        mCurrentSprite = mCurrentSprite % mSprites.size();
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderQueue) {
        Sprite s = mSprites.get(mCurrentSprite);
        s.setPosition(mTransform.x, mTransform.y);
        s.setColor(1,1,1,0.7f);
        renderQueue.add(new SpriteRenderable(s), Constants.QUITE_A_LOT);
    }

}
