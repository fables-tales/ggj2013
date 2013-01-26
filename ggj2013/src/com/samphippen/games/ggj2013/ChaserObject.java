package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ChaserObject implements GameObject {

    private Sprite mSprite;

    private Vector2 mPosition;

    private int mOutOfLight;

    public ChaserObject() {
        mSprite = GameServices.loadSprite("chaser.png");
        mSprite.setColor(3.0f, 0.1f, 0.1f, 1.0f);
        mPosition = new Vector2(3000, 3000);
    }

    @Override
    public void update() {
        PlayerObject player = PlayerObject.getInstance();
        if (player.getPosition().len() > Constants.getFloat("safe_zone_size")) {
            Vector2 playerPosition = player.getPosition();

            Vector2 delta = new Vector2(playerPosition).sub(mPosition);
            if (delta.len() > 200) {
                mOutOfLight++;
            } else if (GameServices.getTicks() % 1 == 0) {
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
                float x = (float) ((float) radius * Math.cos(theta));
                float y = (float) ((float) radius * Math.sin(theta));
                Vector2 position = new Vector2(playerPosition).add(x, y);
                mPosition = position;
            }
            // Make heartbeat fast if near
            if (mPosition.dst(playerPosition) < Constants.sConstants
                    .get("chaser_heart_attack_distance_trigger")) {
                player.HeartBeatParameters.setHeartBeatFast();
                GameHolder.getInstance().redPulse();
        		player.HeartBeatParameters.chaserPulseCount = 0;
            }
            if (mPosition.dst(playerPosition) > Constants.sConstants
                    .get("chaser_heart_attack_distance_trigger") && player.HeartBeatParameters.chaserPulseCount >= 5) {
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
