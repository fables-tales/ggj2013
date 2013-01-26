package com.samphippen.games.ggj2013;

import java.util.List;

public interface GameObject {
    public void update();

    public void emitRenderables(List<Renderable> renderables);
}
