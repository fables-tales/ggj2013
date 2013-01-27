package com.samphippen.games.ggj2013.quadtree;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Quadtree<E> {
    private class Element {
        public final Vector2 mPosition;
        public E mElement;

        public Element(Vector2 pos) {
            mPosition = pos.cpy();
        }
    }

    private final Rectangle mBounds;
    private final List<Element> mElements;
    private final int mMaxCapacity;

    private Quadtree<E> mTopLeft;
    private Quadtree<E> mTopRight;
    private Quadtree<E> mBotLeft;
    private Quadtree<E> mBotRight;

    public Quadtree(float size, int elemPerQuad) {
        this(0, 0, size, elemPerQuad);
    }

    public Quadtree(float x, float y, float size, int elemPerQuad) {
        mBounds = new Rectangle(x, y, size, size);
        ArrayList<Element> elementList = new ArrayList<Element>();
        elementList.ensureCapacity(elemPerQuad);
        mElements = elementList;
        mMaxCapacity = elemPerQuad;
    }

    protected boolean set(Element e) {
        if (mElements.size() + 1 <= mMaxCapacity) {
            mElements.add(e);
            return true;
        }
        return false;
    }

    public int maxElem() {
        return mMaxCapacity;
    }

    private boolean insertElement(Element elem) {
        if (!mBounds.contains(elem.mPosition.x, elem.mPosition.y)) {
            return false;
        }
        if (set(elem)) {
            return true;
        }
        subdivide();
        if (mTopRight.insertElement(elem)) {
            return true;
        }
        if (mTopLeft.insertElement(elem)) {
            return true;
        }
        if (mBotRight.insertElement(elem)) {
            return true;
        }
        if (mBotLeft.insertElement(elem)) {
            return true;
        }
        assert false : "could not subdivide";
        return false;
    }

    public boolean insert(Vector2 position, E e) {
        Element elem = new Element(position);
        elem.mElement = e;
        return insertElement(elem);
    }

    public void query(Rectangle region, List<E> results) {
        if (!mBounds.overlaps(region)) {
            return;
        }
        for (Element e : mElements) {
            if (mBounds.contains(e.mPosition.x, e.mPosition.y)) {
                results.add(e.mElement);
            }
        }
        if (!hasChildren()) {
            return;
        }
        mTopLeft.query(region, results);
        mTopRight.query(region, results);
        mBotLeft.query(region, results);
        mBotRight.query(region, results);
    }

    public boolean hasChildren() {
        return mTopLeft != null;
    }

    protected boolean subdivide() {
        if (hasChildren()) {
            return false;
        }
        float hs = mBounds.width / 2.0f;
        mTopLeft = new Quadtree<E>(mBounds.x, mBounds.y + hs, hs, mMaxCapacity);
        mTopRight = new Quadtree<E>(mBounds.x + hs, mBounds.y + hs, hs,
                mMaxCapacity);
        mBotLeft = new Quadtree<E>(mBounds.x, mBounds.y, hs, mMaxCapacity);
        mBotRight = new Quadtree<E>(mBounds.x + hs, mBounds.y, hs, mMaxCapacity);
        return true;
    }
}
