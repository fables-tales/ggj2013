package com.samphippen.games.ggj2013;

public class HeartBeatParameters {

    private int mMaxRadius;
    private boolean mFastHeartbeat;
    private int mElapsedFastPulses = 0;
    private final double BIG_RADIUS = Constants.sConstants
            .get("heartbeat_radius");
    private final double SMALL_RADIUS = Constants.sConstants
            .get("heartbeat_fast_radius");
    private int mChaserPulseCount = 0;
    private int mTreePulseCount = 0;

    public HeartBeatParameters() {
        mMaxRadius = (int) BIG_RADIUS;
        mFastHeartbeat = false;
    }

    public void setHeartBeatFast() {
        mElapsedFastPulses = 0;
        mMaxRadius = (int) SMALL_RADIUS;
        mFastHeartbeat = true;
    }

    public void setHeartBeatSlow() {
        mMaxRadius = (int) BIG_RADIUS;
        mFastHeartbeat = false;
    }

    public int getMaxRadius() {
        return mMaxRadius;
    }

    public boolean isFastHeartbeat() {
        return mFastHeartbeat;
    }

    public int getElapsedFastPulses() {
        return mElapsedFastPulses;
    }

    public void setElapsedFastPulses(int elapsedFastPulses) {
        mElapsedFastPulses = elapsedFastPulses;
    }

    public int getChaserPulseCount() {
        return mChaserPulseCount;
    }

    public void setChaserPulseCount(int chaserPulseCount) {
        mChaserPulseCount = chaserPulseCount;
    }

    public int getTreePulseCount() {
        return mTreePulseCount;
    }

    public void setTreePulseCount(int treePulseCount) {
        mTreePulseCount = treePulseCount;
    }
}
