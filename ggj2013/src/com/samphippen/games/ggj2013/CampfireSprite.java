package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class CampfireSprite implements GameObject {

    private Sprite mOnSprite;
    private final Sprite mOffSprite;

    private final List<Sprite> mOnSprites = new ArrayList<Sprite>();
    private final List<Sprite> mTransitionSprites = new ArrayList<Sprite>();

    private boolean mOn = true;
    private int mTransitionFrame = 0;
    private Vector2 mPosition;
    private final static Vector2 SPRITE_OFFSET = new Vector2(-56.0f, -46.0f);

    private final Light mLight;

    public CampfireSprite(Light light) {
        mLight = light;
        mLight.setInnerRadius(10.0f);
        mLight.setOuterRadius(50.0f);
        mLight.setIntensity(0.7f);
        loadFrames(mOnSprites, 0, 24, "campfire_large_", ".png");
        loadFrames(mTransitionSprites, 30, 59, "campfire_transition_", ".png");
        mOffSprite = GameServices.loadSprite("campfire-off.png");
    }
    
    public boolean getOn() {
        return mOn;
    }

    public void loadFrames(List<Sprite> out, int start, int end, String prefix,
            String suffix) {
        for (int i = start; i <= end; i++) {
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
        if (!mOn) {
            ++mTransitionFrame;
        }
        mFrameCount++;
        mOnSprite = mOnSprites.get(mFrameCount % mOnSprites.size());
        boolean paniced = GameHolder.getInstance().mPulseG < 0.999;
        if (PlayerObject.getInstance().getPosition().dst(mPosition) < 40
                && !paniced) {
            mOn = false;
            mLight.setInnerRadius(3.0f);
            mLight.setOuterRadius(50.0f);
            mLight.setIntensity(0.3f);
        }
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderQueue) {
        Vector2 drawPosition = mPosition.cpy().add(SPRITE_OFFSET);
        int zOrder = (int) mPosition.y + 10;
        if (mOn) {
            mOnSprite.setPosition(drawPosition.x, drawPosition.y);
            renderQueue.add(new SpriteRenderable(mOnSprite), zOrder);
        } else {
            if (mTransitionFrame >= mTransitionSprites.size()) {
                mOffSprite.setPosition(drawPosition.x, drawPosition.y + 5.0f);
                renderQueue.add(new SpriteRenderable(mOffSprite), zOrder);
            } else {
                Sprite newSprite = mTransitionSprites.get(mTransitionFrame);
                newSprite.setPosition(drawPosition.x, drawPosition.y);
                renderQueue.add(new SpriteRenderable(newSprite), zOrder);
            }
        }
    }

    // public void draw(SpriteBatch mPathBatch) {
    // if (mOn) {
    // mOnSprite.setPosition(mPosition.x - mOnSprite.getWidth() / 2,
    // mPosition.y - mOnSprite.getHeight() / 2);
    // mOnSprite.draw(mPathBatch);
    // } else {
    // mOffSprite.setPosition(mPosition.x - mOffSprite.getWidth() / 2,
    // mPosition.y - mOffSprite.getHeight() / 2);
    // mOffSprite.draw(mPathBatch);
    // }
    // }

}
