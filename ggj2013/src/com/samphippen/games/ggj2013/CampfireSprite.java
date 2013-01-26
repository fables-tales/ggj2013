package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class CampfireSprite implements GameObject {

    private Sprite mOnSprite;
    private Sprite mOffSprite;

    private boolean mOn = true;
    private Vector2 mPosition;

    public CampfireSprite() {
        mOnSprite = GameServices.loadSprite("campfire.png");
        mOffSprite = GameServices.loadSprite("campfire-off.png");
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
        if (PlayerObject.getInstance().getPosition().dst(mPosition) < 40) {
            System.out.println("off!");
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
            mOnSprite.setPosition(mPosition.x-mOnSprite.getWidth()/2, mPosition.y-mOnSprite.getHeight()/2);
            mOnSprite.draw(mPathBatch);
        } else {
            mOffSprite.setPosition(mPosition.x-mOffSprite.getWidth()/2, mPosition.y-mOffSprite.getHeight()/2);
            mOffSprite.draw(mPathBatch);
        }
    }

}
