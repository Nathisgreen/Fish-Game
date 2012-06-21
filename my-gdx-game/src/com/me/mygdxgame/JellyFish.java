package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class JellyFish extends Block{
	
	private Texture jellyTex;
	private TextureRegion[] jellyReg;
	private Animation jellyAni;
	private TextureRegion currentFrame;
	float stateTime = 0f;
	private boolean canHit = true;
	private int hitTimer = 0;

	public JellyFish(Vector2 pos) 
	{
		super(pos);
		
		setBounds(0.5f);
		
		setRotateSpeed(0.025f);
		
		jellyTex = new Texture(Gdx.files.internal("images/sprJellyFish1.png"));
		TextureRegion[][] tmp = TextureRegion.split(jellyTex,jellyTex.getWidth()/ 8, 
				jellyTex.getHeight()/ 1);
		jellyReg = new TextureRegion[1 *8];
		
		int index = 0;
		
		for (int i = 0; i < 8; i++)
		{
			jellyReg[index++] = tmp[0][i];
		}
		
		jellyAni = new Animation(0.4f,jellyReg);

	}
	
	public TextureRegion getCurrentFrame()
	{
		return currentFrame;
	}
	
	
	public void update(float delta)
	{
		super.update(delta);
		
		stateTime += Gdx.graphics.getDeltaTime(); 
		currentFrame = jellyAni.getKeyFrame(stateTime, true);
		
		if (!canHit)
		{
			if (hitTimer < 30)
			{
				hitTimer++;
			}
			else
			{
				hitTimer = 0;
				canHit = true;
			}
		}
		
	}

	public boolean checkInside(Rectangle aBox) {
		
		float yy = WorldRenderer.getCameraHeight() - getPosition().y;
		float y1 = aBox.y;

		if ((aBox.x> position.x * WorldRenderer.ppuX && aBox.x < (position.x + getBounds().width) * WorldRenderer.ppuX)
				|| (aBox.x + aBox.width> position.x * WorldRenderer.ppuX && aBox.x + aBox.width < (position.x + getBounds().width) * WorldRenderer.ppuX))
		{

			if ((y1 > yy * WorldRenderer.ppuY && y1 < (yy + getBounds().height/1.5f) * WorldRenderer.ppuY) ||
					(y1 + (aBox.height * 1.5f) > yy * WorldRenderer.ppuY && y1 + (aBox.height*1.5f )  < (yy + getBounds().height) * WorldRenderer.ppuY))
			{
				if (canHit)
				{
					canHit = false;
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		
		return false;
	}

}
