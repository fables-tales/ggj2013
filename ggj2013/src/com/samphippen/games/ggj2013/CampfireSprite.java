package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class CampfireSprite implements GameObject {

    private Sprite mOnSprite;
    private Sprite mOffSprite;

    private List<Sprite> mOnSprites = new ArrayList<Sprite>();

    private List<Sprite> mOffSprites = new ArrayList<Sprite>();

    private boolean mOn = true;
    private Vector2 mPosition;
    private int count = 0;

    public CampfireSprite() {
        loadFrames(mOnSprites, 24, "campfire_large_", ".png");
        loadFrames(mOffSprites, 20, "campfire_small_", ".png");
    }

    public void loadFrames(List<Sprite> out, int count, String prefix,
            String suffix) {
        for (int i = 0; i < count; i++) {
            String filename = "" + i;
            while (filename.length() < 3) {
                filename = "0" + filename;
            }

            filename = prefix + filename + suffix;
            Sprite s = GameServices.loadSprite(filename);
            out.add(s);
        }
    }

    public void setPosition(Vector2 pos) {
        mPosition = new Vector2(pos);
    }

    public float getX() {
        return mPosition.x;
    }

    public float getY() {
        return mPosition.y;
    }

    private int mFrameCount = 0;

    @Override
    public void update() {
        mFrameCount++;
        mOnSprite = mOnSprites.get(mFrameCount % mOnSprites.size());
        mOffSprite = mOffSprites.get(mFrameCount % mOffSprites.size());
        boolean paniced = GameHolder.getInstance().mPulseG < 0.999;
        if (PlayerObject.getInstance().getPosition().dst(mPosition) < 40
                && !paniced) {
            mOn = false;
        }
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderQueue) {
        if (mOn) {
            mOnSprite.setPosition(mPosition.x, mPosition.y);
            renderQueue.add(new SpriteRenderable(mOnSprite), (int) mPosition.y);
        } else {
            mOffSprite.setPosition(mPosition.x, mPosition.y);
            renderQueue
                    .add(new SpriteRenderable(mOffSprite), (int) mPosition.y);
        }
    }

    public void draw(SpriteBatch mPathBatch) {
        if (mOn) {
            mOnSprite.setPosition(mPosition.x - mOnSprite.getWidth() / 2,
                    mPosition.y - mOnSprite.getHeight() / 2);
            mOnSprite.draw(mPathBatch);
        } else {
            mOffSprite.setPosition(mPosition.x - mOffSprite.getWidth() / 2,
                    mPosition.y - mOffSprite.getHeight() / 2);
            mOffSprite.draw(mPathBatch);
        }
    }

}
