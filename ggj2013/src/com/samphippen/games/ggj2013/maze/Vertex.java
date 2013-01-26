package com.samphippen.games.ggj2013.maze;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;

public class Vertex {
    public Vector2 mPosition;
    
    public Set<Vertex> mConnectedVertices = new HashSet<Vertex>();
    
    public boolean mIsPathable = true;
    
    public Vertex(Vector2 position) {
        mPosition = position;
    }

    public static boolean connected(Vertex other, Vertex v) {
        boolean ab = other.mConnectedVertices.contains(v);
        boolean ba = v.mConnectedVertices.contains(other);
        if (ab) {
            assert ba;
        }
        return ab || ba;
    }

    public void ensureConnected(Vertex v) {
        if (!Vertex.connected(this, v)) {
            this.mConnectedVertices.add(v);
            v.mConnectedVertices.add(this);
        }
    }

    public boolean equals(Vertex obj) {
        return mPosition == obj.mPosition;
    }

    @Override
    public int hashCode() {
        return mPosition.hashCode();
    }
    
    
    
    
}
