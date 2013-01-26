package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.samphippen.games.ggj2013.maze.Graph;

public class GameHolder implements ApplicationListener {
	private OrthographicCamera mCamera;
	private SpriteBatch mBatch;
	private List<GameObject> mWorldObjects = new ArrayList<GameObject>();
	private List<Renderable> mToRender = new ArrayList<Renderable>();
	private final RenderQueueProxy mQueueProxy = new RenderQueueProxy() {
        @Override
        public void add(Renderable renderable) {
            mToRender.add(renderable);
        }
    };
	private Sprite mSprite;
    private Vector2 mCameraOrigin;
    private PlayerObject mPlayer;
    
	
	@Override
	public void create() {	
	    Constants.setConstants();
	    new Graph(30, 30);
		setCameraOrigin(new Vector2(0, 0));
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        mCamera = new OrthographicCamera(w, h);
        mBatch = new SpriteBatch();
        mPlayer = PlayerObject.getInstance();
        mWorldObjects.add(mPlayer);
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
        mPlayer.update();
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        mBatch.setProjectionMatrix(mCamera.combined);
        mBatch.setTransformMatrix(new Matrix4().translate(-getCameraOrigin().x,
                -getCameraOrigin().y, 0));
        
        mToRender.clear();
        for (GameObject o : mWorldObjects) {
            o.emitRenderables(mQueueProxy);
        }
        
        mBatch.begin();
        for (Renderable r : mToRender) {
            r.draw(mBatch);
        }
        mBatch.end();   
    }

    private Vector2 getCameraOrigin() {
	    return mCameraOrigin;
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
