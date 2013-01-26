package com.samphippen.games.ggj2013;

public interface GameObject {
    public void update();

    public void emitRenderables(RenderQueueProxy renderQueue);
}
