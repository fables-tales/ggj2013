package com.samphippen.games.ggj2013;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class InputSystem {
    private static InputSystem sInstance = null;

    private static final float DEAD_ZONE_SIZE = (float) (1.0f * Constants.sConstants
            .get("dead_zone_size"));

    public InputSystem getInstance() {
        if (sInstance == null) {
            sInstance = new InputSystem();
        }

        return sInstance;
    }

    public static Vector2 mouseSpeedVector() {
        float x = Gdx.input.getX() * 1.0f / (GameServices.WIDTH / 2) - 1;
        float y = 1.0f - Gdx.input.getY() * 1.0f / (GameServices.HEIGHT / 2);

        if (Math.hypot(x, y) < DEAD_ZONE_SIZE) {
            x = 0;
            y = 0;
        }

        if (!Gdx.input.isTouched()) {
            x = 0;
            y = 0;
        }

        Vector2 result = new Vector2(x, y);
        if (result.len() > 0) {
            double desiredSize = Constants.sConstants.get("player_speed");
            result.mul((float) (desiredSize / result.len()));
        }
        return result;

    }
}
