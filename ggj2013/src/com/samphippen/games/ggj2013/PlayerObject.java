package com.samphippen.games.ggj2013;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class PlayerObject implements GameObject {

    private static PlayerObject sInstance = null;
    private Sprite mSprite = null;
    public float mHeartbeatRadius = (float) 1.0;
    private Vector2 mPosition = new Vector2(0,0);
    private int mTicks = 0;
    public HeartBeatParameters HeartBeatParameters = new HeartBeatParameters();

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
        mHeartbeatRadius = calculateHeartBeatRadius();
    }
    
    public float calculateHeartBeatRadius(){
    	mTicks++;
        mTicks = mTicks % HeartBeatParameters.getMaxRadius();
        
    	if (HeartBeatParameters.isFastHeartbeat()){
    		if(mTicks == 0){
        		HeartBeatParameters.elapsedFastPulses++;    			
    		}
        	if(HeartBeatParameters.elapsedFastPulses >= NUMPER_FAST_PULSES){
        		HeartBeatParameters.setHeartBeatSlow();
        	}        	
    	}
    	return 3 * mTicks; 
    }
    
    public Vector2 getPosition() {
        return mPosition;
    }

    @Override
    public void emitRenderables(RenderQueueProxy renderables) {
        renderables.add(new SpriteRenderable(mSprite));
    }

}
