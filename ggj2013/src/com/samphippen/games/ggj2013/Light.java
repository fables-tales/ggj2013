package com.samphippen.games.ggj2013;

import com.badlogic.gdx.math.Vector2;

public class Light {
    private float mInnerRadius, mOuterRadius;
    private final Vector2 mPosition;

    Light(Vector2 pos) {
        mPosition = pos.cpy();
    }

    public float getInnerRadius() {
        return mInnerRadius;
    }

    public void setInnerRadius(float mInnerRadius) {
        this.mInnerRadius = mInnerRadius;
    }

    public float getOuterRadius() {
        return mOuterRadius;
    }

    public void setOuterRadius(float mOuterRadius) {
        this.mOuterRadius = mOuterRadius;
    }

    public Vector2 getPosition() {
        return mPosition;
    }
}
