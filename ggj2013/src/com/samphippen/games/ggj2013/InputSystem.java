package com.samphippen.games.ggj2013;

import com.badlogic.gdx.math.Vector2;

public class InputSystem {
    private static InputSystem sInstance = null;
    
    public InputSystem getInstance() {
        if (sInstance == null) {
            sInstance = new InputSystem();
        }
        
        return sInstance;
    }
    
    public Vector2 mouseSpeedVector() {
        return null;
    }
}
