package com.samphippen.games.ggj2013;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class InputSystem {
    private static InputSystem sInstance = null;

    public InputSystem getInstance() {
        if (sInstance == null) {
            sInstance = new InputSystem();
        }

        return sInstance;
    }

    public static Vector2 mouseSpeedVector() {
        float x = Gdx.input.getX() * 1.0f / (GameServices.WIDTH / 2) - 1;
        float y = 1.0f - (Gdx.input.getY() * 1.0f / (GameServices.HEIGHT / 2));

        if (Math.abs(x) < 0.1) {
            x = 0;
        }

        if (Math.abs(y) < 0.1) {
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
