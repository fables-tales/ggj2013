package com.samphippen.games.ggj2013;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class BackgroundObject implements GameObject {

	private SpriteRenderable mSpriteRenderable;
	
	public BackgroundObject(){		
		Sprite sprite = GameServices.loadSprite("newBackground.png");
		sprite.setPosition(-400, -300);
		mSpriteRenderable = new SpriteRenderable(sprite);
	}
	
	@Override
	public void update() {

	}
	
	@Override
	public void emitRenderables(List<Renderable> renderables) {
		renderables.add(mSpriteRenderable);
	}

}
