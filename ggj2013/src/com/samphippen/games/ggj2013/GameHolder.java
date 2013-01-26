
package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.samphippen.games.ggj2013.maze.Graph;

public class GameHolder implements ApplicationListener {
    private OrthographicCamera mCamera;
    private SpriteBatch mBatch;
    private SpriteBatch mSpecialBatch;
    private BackgroundObject mBackground;

    private final List<GameObject> mWorldObjects = new ArrayList<GameObject>();
    private final List<Renderable> mToRender = new ArrayList<Renderable>();
    private final RenderQueueProxy mQueueProxy = new RenderQueueProxy() {
        @Override
        public void add(Renderable renderable) {
            mToRender.add(renderable);
        }
    };
    private Vector2 mCameraOrigin;
    private PlayerObject mPlayer;
    private MouseObject mMouse;

    @Override
    public void create() {
        Constants.setConstants();
        new Graph(30, 30);
        setCameraOrigin(new Vector2(0, 0));
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        mCamera = new OrthographicCamera(w, h);
        mBatch = new SpriteBatch();
        mSpecialBatch = new SpriteBatch();
        mPlayer = PlayerObject.getInstance();
        mBackground = new BackgroundObject();
        mMouse = MouseObject.getInstance();
        mWorldObjects.add(mBackground);
        mWorldObjects.add(mPlayer);
        Gdx.input.setCursorCatched(true);
    }

    private void setCameraOrigin(Vector2 vector2) {
        mCameraOrigin = vector2;
    }

    @Override
    public void dispose() {
        mBatch.dispose();
    }

    @Override
    public void render() {
        update();
        draw();

    }

    private void update() {
        if (Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
            System.exit(1);
        }
        for (GameObject o : mWorldObjects) {
            o.update();
        }
        
        mMouse.update();
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        mBatch.setProjectionMatrix(mCamera.combined);
        mBatch.setTransformMatrix(new Matrix4().translate(-getCameraOrigin().x,
                -getCameraOrigin().y, 0));

        mToRender.clear();
        for (GameObject object : mWorldObjects) {
            object.emitRenderables(mQueueProxy);
        }

        mBatch.begin();
        for (Renderable renderable : mToRender) {
            renderable.draw(mBatch);
        }
        mBatch.end();
        
        mSpecialBatch.setProjectionMatrix(mCamera.combined);
        mSpecialBatch.begin();
        mMouse.draw(mSpecialBatch);
        mSpecialBatch.end();
        
        
    }

    private Vector2 getCameraOrigin() {
        return mPlayer.getPosition();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
