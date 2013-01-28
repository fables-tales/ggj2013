package com.samphippen.games.ggj2013.sound;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.samphippen.games.ggj2013.GameServices;

public class SoundManager {
    private static final int NUM_FOOTSTEPS = 9;
    private static final float PANT_UPDATE_SPEED = 0.93f;
    private static final int SCREECH_DEBOUNCE = 240;
    private static final int THUNK_DEBOUNCE = 40;
    private static final float WIND_VOLUME = 0.25f;
    private final List<Sound> mFootstepSounds = new ArrayList<Sound>();
    private final Sound mHeartBeat;
    private long mHeartBeatInstance = 0;
    private long mLastScreechTick = 0;
    private long mLastStepTick = 0;
    private long mLastThunkTick = 0;
    private final Music mPanting;
    private float mPantingLevel = 0.0f;
    private float mPantingTarget = 0.0f;
    private final Sound mScreech;
    private final Sound mThunk;
    private final Music mWind;

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
        mWind.setVolume(WIND_VOLUME);
    }

    public void beatHeart() {
        mHeartBeat.stop(mHeartBeatInstance);
        mHeartBeatInstance = mHeartBeat.play();
    }

    public void killThings() {
        mPanting.stop();
        mWind.stop();
    }

    public void screech() {
        long currentTick = GameServices.getTicks();
        if (currentTick > mLastScreechTick + SCREECH_DEBOUNCE) {
            mLastScreechTick = currentTick;
            mScreech.play();
        }
    }

    public void setPantingTarget(float level) {
        mPantingTarget = level;
    }

    public void step() {
        float volume = generateStepVolume();
        Sound stepper = pickStepEffect();
        long instance = stepper.play(volume);
        stepper.setPitch(instance, generateStepPitch());
    }

    private float generateStepVolume() {
        long currentTick = GameServices.getTicks();
        float baseVolume = currentTick - mLastStepTick > 35 ? 0.4f : 0.7f;
        mLastStepTick = currentTick;
        float volume = (float) (baseVolume + 0.05 * GameServices.sRng
                .nextGaussian());
        return volume;
    }

    private Sound pickStepEffect() {
        int selectedStep = GameServices.sRng.nextInt(NUM_FOOTSTEPS);
        return mFootstepSounds.get(selectedStep);
    }

    private float generateStepPitch() {
        return (float) Math.pow(2.0,
                0.1 * GameServices.sRng.nextGaussian());
    }

    public void thunk() {
        long currentTick = GameServices.getTicks();
        if (currentTick > mLastThunkTick + THUNK_DEBOUNCE) {
            mLastThunkTick = currentTick;
            mThunk.play();
        }
    }

    public void update() {
        mPantingLevel = mPantingLevel * PANT_UPDATE_SPEED + mPantingTarget
                * (1.0f - PANT_UPDATE_SPEED);
        mPanting.setVolume(mPantingLevel);
    }
}
