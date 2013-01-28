package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class OrangeBlob implements GameObject {

    private final Sprite mSprite;

    public OrangeBlob() {
        mSprite = GameServices.loadSprite("orange_blob.png");
        mSprite.setColor(1, 1, 1, 0);
    }

    private static final float ALPHA_STOP_HIGH = Constants
            .getFloat("blob_alpha_high");
    private static final float ALPHA_STOP_SMALL = Constants
            .getFloat("blob_alpha_low");
    private float mCurrentAlpha = 0;

    private boolean mFadeOut = true;

    private static final float mScaleA = Constants.getFloat("blob_wideness");
    private static final float mScaleB = 3;

    @Override
    public void update() {
        Vector2 pos = GameHolder.getInstance().getFirstOnFire();

        updateAlphaForFade();
        if (pos != null) {
            Vector2 delta = pos.sub(PlayerObject.getInstance().getPosition());

            if ((Math.abs(delta.x) > 400 || Math.abs(delta.y) > 300)
                    && PlayerObject.getInstance().getPosition().len() > Constants
                            .getFloat("safe_zone_size")) {
                mFadeOut = false;
                if (mCurrentAlpha < ALPHA_STOP_HIGH) {
                    mCurrentAlpha += Constants.getFloat("blob_alpha_fade_in");
                } else {
                    mCurrentAlpha = ALPHA_STOP_HIGH;
                }
                if (delta.angle() > 45 && delta.angle() < 45 + 90) {
                    mSprite.setScale(mScaleA, mScaleB);
                    float y = 300;
                    float along = (delta.angle() - 45) / 90.0f;
                    float x = -800 * along + 400;
                    mSprite.setPosition(x - mSprite.getWidth() / 2.0f, y
                            - mSprite.getHeight() / 2.0f);
                }

                if (delta.angle() > 135 && delta.angle() < 135 + 90) {
                    mSprite.setScale(mScaleB, mScaleA);
                    float x = -400;
                    float along = (delta.angle() - 135) / 90.0f;
                    float y = -600 * along + 300;
                    mSprite.setPosition(x - mSprite.getWidth() / 2.0f, y
                            - mSprite.getHeight() / 2.0f);
                }

                if (delta.angle() > 225 && delta.angle() < 225 + 90) {
                    mSprite.setScale(mScaleA, mScaleB);
                    float y = -300;
                    float along = (delta.angle() - 225) / 90.0f;
                    float x = 800 * along - 400;
                    mSprite.setPosition(x - mSprite.getWidth() / 2.0f, y
                            - mSprite.getHeight() / 2.0f);
                }

                if (delta.angle() > 315 && delta.angle() + 360 < 315 + 90) {
                    mSprite.setScale(mScaleB, mScaleA);
                    float x = 400;
                    float along = delta.angle() - 315;
                    if (along < 0) {
                        along += 360;
                    }
                    along /= 90.0;

                    float y = 600 * along + -300;
                    mSprite.setPosition(x - mSprite.getWidth() / 2.0f, y
                            - mSprite.getHeight() / 2.0f);

                }

            } else {
                mFadeOut = true;
            }
        }
    }

    private void updateAlphaForFade() {
        if (mCurrentAlpha > ALPHA_STOP_SMALL && mFadeOut
                && !(mSprite.getX() == 0 && mSprite.getY() == 0)) {
            mCurrentAlpha *= Constants.getFloat("blob_alpha_fade_out");
        } else if (mFadeOut) {
            mCurrentAlpha = ALPHA_STOP_SMALL;
        }
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderQueue) {
    }

    public void draw(SpriteBatch sb) {
        if (mSprite.getX() != 0 && mSprite.getY() != 0) {
            mSprite.setColor(1, 1, 1, mCurrentAlpha);
            mSprite.draw(sb);
        }

    }

}
