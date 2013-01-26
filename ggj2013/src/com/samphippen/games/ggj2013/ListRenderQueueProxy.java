package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class ListRenderQueueProxy implements RenderQueueProxy {
    private final List<Renderable> mRenderables;

    private final class RenderItem implements Comparable<RenderItem> {
        private final int mZOrder;
        private final Renderable mRenderable;

        public RenderItem(Renderable renderable, int zOrder) {
            mZOrder = zOrder;
            mRenderable = renderable;
        }

        @Override
        public int compareTo(RenderItem otherItem) {
            int remoteZOrder = otherItem.getZOrder();
            if (remoteZOrder < mZOrder) {
                return -1;
            } else if (remoteZOrder > mZOrder) {
                return 1;
            } else {
                return 0;
            }
        }

        public int getZOrder() {
            return mZOrder;
        }

        public Renderable getRenderable() {
            return mRenderable;
        }
    }

    private final List<RenderItem> mRenderItems = new ArrayList<RenderItem>();

    public ListRenderQueueProxy(List<Renderable> centralList) {
        mRenderables = centralList;
    }

    @Override
    public void add(Renderable renderable, int zOrder) {
        mRenderItems.add(new RenderItem(renderable, zOrder));
    }

    @Override
    public void commit() {
        // sort mRenderItems
        Collections.sort(mRenderItems);
        for (RenderItem item : mRenderItems) {
            mRenderables.add(item.getRenderable());
        }
    }
}
