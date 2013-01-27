package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ChaserObject implements GameObject {

    private final Sprite mSprite;

    private Vector2 mPosition;
    private int mHighlightFreeze = 0;

    private Light mHighlight = null;

    private int mOutOfLight;
    private boolean mInRadius;

    public ChaserObject() {
        mSprite = GameServices.loadSprite("enemy.png");
        mSprite.setColor(3.0f, 0.1f, 0.1f, 1.0f);
        mPosition = new Vector2(3000, 3000);
    }

    private void highlight() {
        if (mHighlight != null) {
            GameHolder.getInstance().getLightManager().disposeLight(mHighlight);
        }
        Vector2 highlightPosition = mPosition.cpy().add(
                new Vector2(mSprite.getWidth() / 2.0f,
                        mSprite.getHeight() / 2.0f));
        mHighlight = GameHolder.getInstance().getLightManager()
                .createLight(highlightPosition);
        mHighlight.setInnerRadius(100.0f);
        mHighlight.setOuterRadius(120.0f);
        mHighlight.setIntensity(1.0f);
        mHighlightFreeze = 40;
    }

    @Override
    public void update() {
        if (mHighlight != null) {
            final float HIGHLIGHT_DECAY_RATE = 0.98f;
            mHighlight.setIntensity(mHighlight.getIntensity()
                    * HIGHLIGHT_DECAY_RATE);
            if (mHighlight.getIntensity() < 0.05f) {
                GameHolder.getInstance().getLightManager()
                        .disposeLight(mHighlight);
                mHighlight = null;
            }
        }
        if (mHighlightFreeze > 0) {
            --mHighlightFreeze;
        }
        PlayerObject player = PlayerObject.getInstance();
        if (player.getPosition().len() > Constants.getFloat("safe_zone_size")) {
            Vector2 playerPosition = player.getPosition();

            Vector2 delta = new Vector2(playerPosition).sub(mPosition);
            if (delta.len() > Constants.getFloat("chaser_light_distance")) {
                mOutOfLight++;
            } else if (GameServices.getTicks() % 1 == 0
                    && mHighlightFreeze == 0) {
                mPosition.add(delta.mul((float) (1.0f * Constants.sConstants
                        .get("chaser_speed"))));
            }
            if (mOutOfLight >= (float) (1.0f * Constants.sConstants
                    .get("chaser_reappear_delay"))
                    * 60
                    + GameServices.sRng
                            .nextInt((int) (60 * Constants.sConstants
                                    .get("chaser_reappear_extra_random_delay")))) {
                mOutOfLight = 0;
                float theta = (float) (GameServices.sRng.nextFloat() * Math.PI * 2);
                float radius = GameServices.sRng.nextFloat()
                        * (float) (1.0f * Constants.sConstants
                                .get("chaser_spawn_random_radius"))
                        + (float) (1.0f * Constants.sConstants
                                .get("chaser_spawn_constant_spawn_radius"));
                float x = (float) (radius * Math.cos(theta));
                float y = (float) (radius * Math.sin(theta));
                Vector2 position = new Vector2(playerPosition).add(x, y);
                mPosition = position;
            }
            // Make heartbeat fast if near
            if (mPosition.dst(playerPosition) < Constants.sConstants
                    .get("chaser_heart_attack_distance_trigger")) {
                player.HeartBeatParameters.setHeartBeatFast();
                if (!mInRadius) {
                    highlight();
                }
                GameHolder.getInstance().redPulse();
                GameHolder.getInstance().amplifyPulse();
                GameHolder.getInstance().getSoundManager().screech();
                player.HeartBeatParameters.chaserPulseCount = 0;
                mInRadius = true;
            } else {
                mInRadius = false;
            }
            if (mPosition.dst(playerPosition) > Constants.sConstants
                    .get("chaser_heart_attack_distance_trigger")
                    && player.HeartBeatParameters.chaserPulseCount >= 5) {
                GameHolder.getInstance().whitePulse();
            }

        }
        mSprite.setPosition(mPosition.x, mPosition.y);

    }

    @Override
    public void emitRenderables(RenderQueueProxy renderQueue) {
        renderQueue.add(new SpriteRenderable(mSprite), (int) mPosition.y);
    }

    public Vector2 getPosition() {
        return mPosition;
    }

}
