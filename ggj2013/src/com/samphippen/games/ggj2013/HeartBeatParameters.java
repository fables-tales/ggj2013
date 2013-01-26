package com.samphippen.games.ggj2013;

public class HeartBeatParameters {

    private int mMaxRadius = 100;
    private boolean mFastHeartbeat = false;
    
    public void setHeartBeatFast(){
    	mMaxRadius  = 40;
    	mFastHeartbeat = true;
    }

    public void setHeartBeatSlow(){
    	mMaxRadius  = 100;
    	mFastHeartbeat = false;
    }

	public int getMaxRadius() {
		return mMaxRadius;
	}

	public boolean isFastHeartbeat() {
		return mFastHeartbeat;
	}
}
