package com.samphippen.games.ggj2013;

import java.util.List;

public class BackgroundObject implements GameObject {

	private SpriteRenderable mSpriteRenderable;
	
	public BackgroundObject(SpriteRenderable spriteRenderable){
		mSpriteRenderable = new SpriteRenderable("newBackground.png");
	}
	
	@Override
	public void update() {

	}
	
	@Override
	public void emitRenderables(List<Renderable> renderables) {
		renderables.add(mSpriteRenderable);
	}

}
