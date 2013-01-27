package com.samphippen.games.ggj2013.maze;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;

public class Graph {
    private HashMap<Vector2, Vertex> mVertices = new HashMap<Vector2, Vertex>();

    private List<Edge> mEdges = new ArrayList<Edge>();

    public Graph(int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Vertex v = new Vertex(new Vector2(x, y));
                mVertices.put(new Vector2(x, y), v);
            }
        }

        buildGraph(width, height);
    }

    private List<Vertex> shortestPath(Vector2 start, Vector2 end) {
        return null;
        
    }

    private void buildGraph(int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Vertex v = mVertices.get(new Vector2(x, y));
                
                if (x != 0) {
                    Vertex other = mVertices.get(new Vector2(x - 1, y));
                    other.ensureConnected(v);
                }
                
                if (y != 0) {
                    Vertex other = mVertices.get(new Vector2(x, y-1));
                    other.ensureConnected(v);
                }
                
                if (y != height-1) {
                    Vertex other = mVertices.get(new Vector2(x, y+1));
                    other.ensureConnected(v);
                }
                
                if (x != width-1) {
                    Vertex other = mVertices.get(new Vector2(x+1, y));
                    other.ensureConnected(v);
                }
            }
        }
    }
}
