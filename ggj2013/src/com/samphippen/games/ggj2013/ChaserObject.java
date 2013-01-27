package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ChaserObject implements GameObject {

    private final Sprite mSprite;
    private final Sprite mRevealSprite;

    private Vector2 mPosition;
    private int mHighlightFreeze = 0;

    private Light mHighlight = null;

    private int mOutOfLight;
    private boolean mInRadius;

    public ChaserObject() {
        mSprite = GameServices.loadSprite("enemy.png");
        mRevealSprite = GameServices.loadSprite("enemy_reveal.png");
        mSprite.setColor(3.0f, 0.1f, 0.1f, 1.0f);
        mPosition = new Vector2(0, 0);
    }

    private void highlight() {
        if (mHighlight != null) {
            GameHolder.getInstance().getLightManager().disposeLight(mHighlight);
        }
        Vector2 highlightPosition = mPosition.cpy().add(
                new Vector2(mRevealSprite.getWidth() / 2.0f, mRevealSprite
                        .getHeight() / 2.0f));
        mHighlight = GameHolder.getInstance().getLightManager()
                .createLight(highlightPosition);
        mHighlight.setInnerRadius(Constants
                .getFloat("chaser_highlight_inner_radius"));
        mHighlight.setOuterRadius(Constants
                .getFloat("chaser_highlight_outer_radius"));
        mHighlight.setIntensity(Constants
                .getFloat("chaser_highlight_start_intensity"));
        mHighlightFreeze = Constants.getInt("chaser_highlight_freeze_ticks");
    }

    private boolean considerPlayerAtRisk() {
        Vector2 playerPosition = PlayerObject.getInstance().getPosition();
        if (playerPosition.len() < Constants.getFloat("safe_zone_size")) {
            // System.err.println("SAFE: in safe zone");
            return false;

        }
        for (CampfireSprite campfire : GameHolder.getInstance()
                .getPathSprites()) {
            float fireDistance = new Vector2(campfire.getX(), campfire.getY())
                    .dst(playerPosition);
            float fireCutoff = Constants.getFloat("campfire_safe_zone_size");
            if (fireDistance < fireCutoff) {
                // System.err.println("SAFE: near camp fire");
                return false;

            }
        }
        // System.err.println("UNSAFE");
        return true;
    }

    @Override
    public void update() {
        if (mHighlight != null) {
            final float HIGHLIGHT_DECAY_RATE = Constants
                    .getFloat("chaser_highlight_decay_rate");
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
        Vector2 playerPosition = player.getPosition();
        boolean atRisk = considerPlayerAtRisk();
        if (!atRisk && playerPosition.dst(mPosition) < 300.0f) {
            mPosition = new Vector2(playerPosition.cpy().add(1000.0f, 0.0f));
        }
        if (atRisk) {
            Vector2 delta = new Vector2(playerPosition).sub(mPosition);
            if (delta.len() > Constants.getFloat("chaser_light_distance")) {
                mOutOfLight++;
            } else if (GameServices.getTicks() % 1 == 0
                    && mHighlightFreeze == 0) {
                mPosition.add(delta
                        .cpy()
                        .nor()
                        .mul(Constants.getFloat("chaser_constant_speed"))
                        .add(delta.cpy().mul(
                                (float) (1.0f * Constants.sConstants
                                        .get("chaser_speed")))));
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
        mRevealSprite.setPosition(mPosition.x, mPosition.y);
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderQueue) {
        if (mHighlightFreeze > 0) {
            renderQueue.add(new SpriteRenderable(mRevealSprite),
                    -Constants.QUITE_A_LOT);
        } else {
            renderQueue.add(new SpriteRenderable(mSprite), (int) mPosition.y);
        }
    }

    public Vector2 getPosition() {
        return mPosition;
    }

}
