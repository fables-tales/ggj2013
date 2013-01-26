package com.samphippen.games.ggj2013.sound;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private static final int NUM_AMBIENTS = 5;
    private final Sound mHeartBeat;
    private final Sound mFootsteps;
    private long mHeartBeatInstance = 0;
    private long mFootstepInstance = 0;
    private final List<Sound> mAmbientSounds = new ArrayList<Sound>();

    private final boolean DISABLE_AMBIENT = true;
    private final boolean DISABLE_HEARTBEAT = false;
    private final boolean DISABLE_FOOTSTEPS = true;

    public SoundManager() {
        if (!DISABLE_HEARTBEAT) {
            mHeartBeat = Gdx.audio.newSound(Gdx.files
                    .internal("bin/data/heartbeat-excited.wav"));
        } else {
            mHeartBeat = null;
        }
        if (!DISABLE_FOOTSTEPS) {
            mFootsteps = Gdx.audio.newSound(Gdx.files
                    .internal("bin/data/footsteps.wav"));
        } else {
            mFootsteps = null;
        }
        if (!DISABLE_AMBIENT) {
            for (int i = 0; i < NUM_AMBIENTS; ++i) {
                mAmbientSounds.add(Gdx.audio.newSound(Gdx.files
                        .internal(new Formatter().format(
                                "bin/data/ambient_%d.wav", i).toString())));
            }
        }
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
        mFootsteps.stop(mFootstepInstance);
        mFootstepInstance = mFootsteps.play();
    }
}
