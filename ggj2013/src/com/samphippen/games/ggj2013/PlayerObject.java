package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class PlayerObject implements GameObject {

    private static PlayerObject sInstance = null;
    private Sprite mSprite = null;
    public float mHeartbeatRadius = (float) 1.0;
    private Vector2 mPosition = new Vector2(0,0);
    private int mTicks = 0;
    private int elapsedFastPulses = 0;
    public HeartBeatParameters HeartBeatParameters = new HeartBeatParameters();

    private final Double SLOW_HEARTBEAT = Constants.sConstants.get("heartbeat_rate");
    private final Double FAST_HEARTBEAT = Constants.sConstants.get("heartbeat_increased_rate");
    private final Double NUMPER_FAST_PULSES = Constants.sConstants.get("number_fast_pulses");

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
        mPosition.add(InputSystem.mouseSpeedVector());
        mSprite.setPosition(mPosition.x, mPosition.y);
        mTicks++;
        mHeartbeatRadius = calculateHeartBeatRadius();
    }
    
    public float calculateHeartBeatRadius(){
    	if (HeartBeatParameters.isFastHeartbeat()){
        	elapsedFastPulses++;
        	if(elapsedFastPulses == NUMPER_FAST_PULSES){
        		elapsedFastPulses =0;
        		HeartBeatParameters.setHeartBeatSlow();
        	}        		
        	return (float)Math.sin(mTicks/FAST_HEARTBEAT)+1.0f; 
    	}
    	else{
    		return (float)Math.sin(mTicks/SLOW_HEARTBEAT)+1.0f;    		
    	}    	
    }
    
    public Vector2 getPosition() {
        return mPosition;
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderables) {
        renderables.add(new SpriteRenderable(mSprite));
    }

}
