package com.samphippen.games.ggj2013;

import java.util.List;

final class ListRenderQueueProxy implements RenderQueueProxy {
    private final List<Renderable> mRenderables;

    public ListRenderQueueProxy(List<Renderable> centralList) {
        mRenderables = centralList;
    }

    @Override
    public void add(Renderable renderable) {
        mRenderables.add(renderable);
    }
}
