package com.samphippen.games.ggj2013;

public interface RenderQueueProxy {
    public void add(Renderable renderable, int zOrder);

    public void commit();
}
