package com.samphippen.games.ggj2013.sound;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.samphippen.games.ggj2013.GameServices;

public class SoundManager {
    private static final int NUM_FOOTSTEPS = 9;
    private final Sound mHeartBeat;
    private final Sound mThunk;
    private final Sound mScreech;
    private final Music mPanting;
    private final Music mWind;
    private float mPantingTarget = 0.0f;
    private float mPantingLevel = 0.0f;
    private long mHeartBeatInstance = 0;
    private long mLastThunkTick = 0;
    private long mLastScreechTick = 0;
    private long mLastStepTick = 0;
    private final List<Sound> mFootstepSounds = new ArrayList<Sound>();

    public SoundManager() {
        mHeartBeat = Gdx.audio.newSound(Gdx.files
                .internal("bin/data/heartbeat-excited.wav"));
        for (int i = 0; i < NUM_FOOTSTEPS; ++i) {
            mFootstepSounds.add(Gdx.audio.newSound(Gdx.files
                    .internal("bin/data/footsteps" + i + ".wav")));
        }
        mThunk = Gdx.audio.newSound(Gdx.files.internal("bin/data/thunk.wav"));
        mScreech = Gdx.audio.newSound(Gdx.files
                .internal("bin/data/enemy-tmp.wav"));
        mPanting = Gdx.audio.newMusic(Gdx.files
                .internal("bin/data/panting.wav"));
        mPanting.setLooping(true);
        mPanting.play();
        mPanting.setVolume(0);
        mWind = Gdx.audio.newMusic(Gdx.files.internal("bin/data/wind.wav"));
        mWind.play();
        mWind.setLooping(true);
        mWind.setVolume(0.25f);
    }

    public void update() {
        final float UPDATE_SPEED = 0.93f;
        mPantingLevel = mPantingLevel * UPDATE_SPEED + mPantingTarget
                * (1.0f - UPDATE_SPEED);
        mPanting.setVolume(mPantingLevel);
    }

    public void beatHeart() {
        mHeartBeat.stop(mHeartBeatInstance);
        mHeartBeatInstance = mHeartBeat.play();
    }

    public void step() {
        float pitch = (float) Math.pow(2.0,
                0.1 * GameServices.sRng.nextGaussian());
        long currentTick = GameServices.getTicks();
        float baseVolume = currentTick - mLastStepTick > 35 ? 0.4f : 0.7f;
        mLastStepTick = currentTick;
        float volume = (float) (baseVolume + 0.05 * GameServices.sRng
                .nextGaussian());
        int selectedStep = GameServices.sRng.nextInt(NUM_FOOTSTEPS);
        Sound stepper = mFootstepSounds.get(selectedStep);
        long instance = stepper.play(volume);
        stepper.setPitch(instance, pitch);
    }

    public void thunk() {
        long currentTick = GameServices.getTicks();
        if (currentTick > mLastThunkTick + 40) {
            mLastThunkTick = currentTick;
            mThunk.play();
        }
    }

    public void screech() {
        long currentTick = GameServices.getTicks();
        if (currentTick > mLastScreechTick + 240) {
            mLastScreechTick = currentTick;
            mScreech.play();
        }
    }

    public void setPantingTarget(float level) {
        mPantingTarget = level;
    }

    public void killThings() {
        mPanting.stop();
        mWind.stop();
    }
}
