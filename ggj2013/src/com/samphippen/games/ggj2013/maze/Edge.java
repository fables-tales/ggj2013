package com.samphippen.games.ggj2013.maze;

import com.badlogic.gdx.math.Vector2;

public class Edge {
    public Vector2 mVertex1;
    public Vector2 mVertex2;
    
    public Edge(Vector2 a, Vector2 b) {
        mVertex1 = a;
        mVertex2 = b;
    }
    
    public boolean connects(Vector2 a, Vector2 b) {
        return (mVertex1 == a && mVertex2 == b) || (mVertex1 == b && mVertex2 == a);
    }
}
