package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class CampfireSprite implements GameObject {

    private Sprite mOnSprite;
    private Sprite mOffSprite;

    private final List<Sprite> mOnSprites = new ArrayList<Sprite>();

    private final List<Sprite> mOffSprites = new ArrayList<Sprite>();

    private boolean mOn = true;
    private Vector2 mPosition;
    private final static Vector2 SPRITE_OFFSET = new Vector2(-56.0f, -64.0f);

    private final Light mLight;

    public CampfireSprite(Light light) {
        mLight = light;
        mLight.setInnerRadius(10.0f);
        mLight.setOuterRadius(50.0f);
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
            mLight.setInnerRadius(3.0f);
            mLight.setOuterRadius(28.0f);
        }
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderQueue) {
        Vector2 drawPosition = mPosition.cpy().add(SPRITE_OFFSET);
        if (mOn) {
            mOnSprite.setPosition(drawPosition.x, drawPosition.y);
            renderQueue.add(new SpriteRenderable(mOnSprite),
                    (int) mPosition.y + 10);
        } else {
            mOffSprite.setPosition(drawPosition.x, drawPosition.y);
            renderQueue.add(new SpriteRenderable(mOffSprite),
                    (int) mPosition.y + 10);
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
