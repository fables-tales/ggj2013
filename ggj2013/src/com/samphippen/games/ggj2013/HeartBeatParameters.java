package com.samphippen.games.ggj2013;

public class HeartBeatParameters {

    private int mMaxRadius;
    private boolean mFastHeartbeat;
    public int elapsedFastPulses = 0;
    private final double BIG_RADIUS = Constants.sConstants.get("heartbeat_radius");
    private final double SMALL_RADIUS = Constants.sConstants.get("heartbeat_fast_radius");
	private Double mHeartbeat;
    
	public HeartBeatParameters(){
	    mMaxRadius = (int) BIG_RADIUS;
	    mFastHeartbeat = false;
	}
	
    public void setHeartBeatFast(){
    	elapsedFastPulses = 0;
    	mMaxRadius  = (int) SMALL_RADIUS;
    	mFastHeartbeat = true;
    }

    public void setHeartBeatSlow(){
    	mMaxRadius  = (int) BIG_RADIUS;
    	mFastHeartbeat = false;
    }

	public int getMaxRadius() {
		return mMaxRadius;
	}

	public boolean isFastHeartbeat() {
		return mFastHeartbeat;
	}
	
	public Double getmHeartbeat() {
		return mHeartbeat;
	}
}
