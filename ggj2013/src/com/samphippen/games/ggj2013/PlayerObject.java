package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class PlayerObject implements GameObject {

    private static PlayerObject sInstance = null;
    private Sprite mSprite = null;
    public float mHeartbeatRadius = (float) 1.0;
    private Vector2 mPrevPosition = new Vector2(0, 0);
    private Vector2 mPosition = new Vector2(0, 0);
    public int mTicks = 0;
    public HeartBeatParameters HeartBeatParameters = new HeartBeatParameters();
    private double incrementTicks = Constants.sConstants.get("heartbeat_speed_multiplier");

    private final Double NUMPER_FAST_PULSES = Constants.sConstants
            .get("number_fast_pulses");

    private PlayerObject() {
        mSprite = GameServices.loadSprite("color.png");
    }

    public static PlayerObject getInstance() {
        if (sInstance == null) {
            sInstance = new PlayerObject();
        }

        return sInstance;
    }

    @Override
    public void update() {
        mPrevPosition = mPosition.cpy();
        mPosition.add(InputSystem.mouseSpeedVector());
        mSprite.setPosition(mPosition.x, mPosition.y);
        mHeartbeatRadius = calculateHeartBeatRadius();
    }

    public void rejectMovement() {
        mPosition = mPrevPosition;
    }

    public float calculateHeartBeatRadius() {
        mTicks += incrementTicks;
        if(mTicks >= HeartBeatParameters.getMaxRadius()){
        	mTicks = 0;
        }

        if (mTicks == 0) {
            GameHolder.getInstance().getSoundManager().beatHeart();
        }

        if (HeartBeatParameters.isFastHeartbeat()) {
            if (mTicks == 0) {
                HeartBeatParameters.elapsedFastPulses++;
            }
            if (HeartBeatParameters.elapsedFastPulses >= NUMPER_FAST_PULSES) {
                HeartBeatParameters.setHeartBeatSlow();
            }
        }
        return (float) (mTicks*incrementTicks);
    }

    public Vector2 getPosition() {
        return mPosition;
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderables) {
        renderables.add(new SpriteRenderable(mSprite), (int) mPosition.y);
    }

}
