package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class LightManager {
    private final List<Light> mLights = new ArrayList<Light>();

    Light createLight(Vector2 position) {
        Light l = new Light(position);
        mLights.add(l);
        return l;
    }

    void disposeLight(Light light) {
        mLights.remove(light);
    }

    List<Light> lightsNearest(final Vector2 point, int nLights) {
        Comparator<Light> lightCompar = new Comparator<Light>() {
            private float lightWeight(Light l) {
                return point.dst(l.getPosition());
            }

            @Override
            public int compare(Light lhs, Light rhs) {
                float weightLeft = lightWeight(lhs);
                float weightRight = lightWeight(rhs);
                if (weightLeft < weightRight) {
                    return -1;
                } else if (weightLeft > weightRight) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
        Collections.sort(mLights, lightCompar);
        try {
            return Collections.unmodifiableList(mLights.subList(0, nLights));
        } catch (IndexOutOfBoundsException tooFewLightsException) {
            assert mLights.size() <= nLights;
            return Collections.unmodifiableList(mLights);
        }
    }
}
