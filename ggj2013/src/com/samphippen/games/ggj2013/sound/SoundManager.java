package com.samphippen.games.ggj2013.sound;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.samphippen.games.ggj2013.GameServices;

public class SoundManager {
    private static final int NUM_AMBIENTS = 5;
    private static final int NUM_FOOTSTEPS = 9;
    private final Sound mHeartBeat;
    private final Sound mThunk;
    private final Sound mScreech;
    private long mHeartBeatInstance = 0;
    private long mLastThunkTick = 0;
    private long mLastScreechTick = 0;
    private final List<Sound> mAmbientSounds = new ArrayList<Sound>();
    private final List<Sound> mFootstepSounds = new ArrayList<Sound>();

    private final boolean DISABLE_AMBIENT = true;
    private final boolean DISABLE_HEARTBEAT = false;
    private final boolean DISABLE_FOOTSTEPS = false;

    public SoundManager() {
        if (!DISABLE_HEARTBEAT) {
            mHeartBeat = Gdx.audio.newSound(Gdx.files
                    .internal("bin/data/heartbeat-excited.wav"));
        } else {
            mHeartBeat = null;
        }
        if (!DISABLE_FOOTSTEPS) {
            for (int i = 0; i < NUM_FOOTSTEPS; ++i) {
                mFootstepSounds.add(Gdx.audio.newSound(Gdx.files
                        .internal("bin/data/footsteps" + i + ".wav")));
            }
        }
        if (!DISABLE_AMBIENT) {
            for (int i = 0; i < NUM_AMBIENTS; ++i) {
                mAmbientSounds.add(Gdx.audio.newSound(Gdx.files
                        .internal(new Formatter().format(
                                "bin/data/ambient_%d.wav", i).toString())));
            }
        }
        mThunk = Gdx.audio.newSound(Gdx.files.internal("bin/data/thunk.wav"));
        mScreech = Gdx.audio.newSound(Gdx.files
                .internal("bin/data/enemy-tmp.wav"));
    }

    public void update() {
    }

    public void beatHeart() {
        if (DISABLE_HEARTBEAT) {
            return;
        }
        mHeartBeat.stop(mHeartBeatInstance);
        mHeartBeatInstance = mHeartBeat.play();
    }

    public void step() {
        if (DISABLE_FOOTSTEPS) {
            return;
        }
        float pitch = (float) Math.pow(2.0,
                0.1 * GameServices.sRng.nextGaussian());
        float volume = (float) (0.4 + 0.05 * GameServices.sRng.nextGaussian());
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
}
